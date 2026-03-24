package cn.ms.dm.domain.log;

import cn.ms.dm.domain.base.BaseLog;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name LoginLog
 * @date 2025-08-14 15:03
 * @email lmtemail163@163.com
 * @description 登录日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("log_login")
@Schema(description = "登录日志")
public class LoginLog extends BaseLog {
    @Serial
    private static final long serialVersionUID = -1300857274062130624L;

    @Schema(description = "账号 ID")
    @TableField
    private Long accountId;


    @Schema(description = "登录IP")
    @TableField
    private String ip;

}
