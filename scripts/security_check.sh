#!/bin/bash

# ==============================================================================
# Linux 主机安全快速检查脚本
# 功能：检查网络连接、日志、进程、定时任务等，生成分析报告
# 输出：当前目录下的 check.log
# 用法：sudo ./security_check.sh
# ==============================================================================

LOG_FILE="check.log"
DATE_NOW=$(date '+%Y-%m-%d %H:%M:%S')

# 初始化日志文件
echo "==============================================================================" > "$LOG_FILE"
echo "Linux 主机安全检测报告" >> "$LOG_FILE"
echo "生成时间: $DATE_NOW" >> "$LOG_FILE"
echo "主机名: $(hostname)" >> "$LOG_FILE"
echo "内核版本: $(uname -r)" >> "$LOG_FILE"
echo "==============================================================================" >> "$LOG_FILE"
echo ""

# 定义一个函数来打印分割线和标题
print_section() {
    echo "" >> "$LOG_FILE"
    echo "------------------------------------------------------------------------------" >> "$LOG_FILE"
    echo "【$1】" >> "$LOG_FILE"
    echo "$2" >> "$LOG_FILE"
    echo "------------------------------------------------------------------------------" >> "$LOG_FILE"
}

# 定义一个函数来执行命令并捕获输出（即使命令失败也不中断脚本）
run_cmd() {
    local cmd=$1
    local desc=$2
    echo ">>> 执行命令: $cmd" >> "$LOG_FILE"
    echo ">>> 说明: $desc" >> "$LOG_FILE"
    echo "--- 开始输出 ---" >> "$LOG_FILE"
    # 使用 eval 执行命令，将 stdout 和 stderr 都重定向到日志，如果命令失败显示错误信息但不退出
    eval "$cmd" >> "$LOG_FILE" 2>&1 || echo "[命令执行出错或无权限]" >> "$LOG_FILE"
    echo "--- 结束输出 ---" >> "$LOG_FILE"
    echo "" >> "$LOG_FILE"
}

# 1. 检查异常网络连接
print_section "1. 网络连接状态检查" "检查是否有大量异常连接、SYN_RECV 状态或陌生 IP 高频访问。"

run_cmd "ss -tunap" "查看所有 TCP/UDP 连接及对应进程"

run_cmd "ss -tn | awk '{print \$5}' | cut -d: -f1 | sort | uniq -c | sort -nr | head -n 20" "统计连接数最多的前 20 个远程 IP (警惕单个 IP 连接数过高)"

run_cmd "ss -tn | grep SYN-RECV | wc -l" "统计处于 SYN-RECV 状态的连接数 (若数值很大，可能正在遭受 SYN Flood 攻击)"

# 2. 检查系统日志 (暴力破解痕迹)
print_section "2. 认证日志检查 (暴力破解)" "查找 SSH 登录失败记录。根据发行版不同，日志路径可能不同。"

# 尝试判断日志路径
if [ -f /var/log/secure ]; then
    LOG_PATH="/var/log/secure"
    echo "检测到日志文件: $LOG_PATH" >> "$LOG_FILE"
    run_cmd "grep 'Failed password' $LOG_PATH | tail -n 50" "最近 50 条失败密码尝试记录"
    run_cmd "grep 'Failed password' $LOG_PATH | awk '{print \$(NF-3)}' | sort | uniq -c | sort -nr | head -n 10" "失败次数最多的前 10 个 IP"
elif [ -f /var/log/auth.log ]; then
    LOG_PATH="/var/log/auth.log"
    echo "检测到日志文件: $LOG_PATH" >> "$LOG_FILE"
    run_cmd "grep 'Failed password' $LOG_PATH | tail -n 50" "最近 50 条失败密码尝试记录"
    run_cmd "grep 'Failed password' $LOG_PATH | awk '{print \$(NF-3)}' | sort | uniq -c | sort -nr | head -n 10" "失败次数最多的前 10 个 IP"
else
    echo "未找到常见的认证日志文件 (/var/log/secure 或 /var/log/auth.log)。" >> "$LOG_FILE"
    echo "可能是日志路径不同，或使用了 systemd-journald。" >> "$LOG_FILE"
    run_cmd "journalctl -u ssh --since '1 hour ago' | grep 'Failed'" "尝试从 journalctl 获取最近 1 小时的 SSH 失败记录"
fi

# 3. 检查可疑进程和资源
print_section "3. 进程与资源占用检查" "检查是否有未知进程占用大量 CPU/内存，或名称异常的进程。"

run_cmd "ps -eo pid,user,%cpu,%mem,comm --sort=-%cpu | head -n 20" "CPU 占用最高的前 20 个进程"

run_cmd "ps -eo pid,user,%cpu,%mem,comm --sort=-%mem | head -n 20" "内存占用最高的前 20 个进程"

run_cmd "ps -ef --sort=-start_time | head -n 20" "最近启动的前 20 个进程 (关注非系统用户启动的奇怪进程)"

# 4. 检查定时任务 (后门排查)
print_section "4. 定时任务检查" "攻击者常利用 crontab 进行持久化驻留。"

run_cmd "cat /etc/crontab" "系统级定时任务 (/etc/crontab)"

run_cmd "ls -la /etc/cron.d/" "系统级定时任务目录 (/etc/cron.d/) 内容"

run_cmd "for user in \$(cut -f1 -d: /etc/passwd); do echo \"=== User: \$user ===\"; crontab -u \$user -l 2>/dev/null; done" "遍历所有用户的个人定时任务"

# 5. 检查登录历史
print_section "5. 用户登录历史" "检查最近的成功和失败登录记录。"

run_cmd "last | head -n 20" "最近 20 条成功登录记录"

run_cmd "lastb | head -n 20" "最近 20 条失败登录记录 (需要 root 权限，若为空则表示无记录或无权限)"

run_cmd "who" "当前在线用户"

# 6. 检查历史命令 (仅供参考)
print_section "6. 历史命令检查" "查看当前用户的历史命令，注意攻击者可能会清除历史记录。"

run_cmd "history | tail -n 50" "当前用户最近 50 条历史命令"

# 7. 检查常见后门文件位置 (简单扫描)
print_section "7. 常见可疑文件位置扫描" "检查 /tmp, /var/tmp 等目录下最近修改的可执行文件。"

run_cmd "find /tmp /var/tmp -type f -mtime -1 -ls 2>/dev/null" "过去 1 天内在 /tmp 和 /var/tmp 修改过的文件"

run_cmd "ls -la /root/.ssh/authorized_keys 2>/dev/null" "检查 root 用户的免密登录密钥 (警惕未知的公钥)"

# 结尾总结
echo "" >> "$LOG_FILE"
echo "==============================================================================" >> "$LOG_FILE"
echo "检查完成。" >> "$LOG_FILE"
echo "请人工分析上述日志：" >> "$LOG_FILE"
echo "1. 是否有单个 IP 发起大量连接？" >> "$LOG_FILE"
echo "2. 是否有大量 SSH 失败记录？" >> "$LOG_FILE"
echo "3. 是否有未知的进程占用高资源？" >> "$LOG_FILE"
echo "4. 定时任务中是否有奇怪的脚本执行命令？" >> "$LOG_FILE"
echo "5. authorized_keys 中是否有陌生的公钥？" >> "$LOG_FILE"
echo "==============================================================================" >> "$LOG_FILE"

echo "报告已生成：$(pwd)/$LOG_FILE"