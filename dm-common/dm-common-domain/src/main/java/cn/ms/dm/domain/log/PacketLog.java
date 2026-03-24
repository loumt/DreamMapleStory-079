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
 * @name PacketLog
 * @date 2026-02-27 9:02
 * @email lmtemail163@163.com
 * @description
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "数据包日志")
@TableName("log_packet")
public class PacketLog extends BaseLog {
    @Serial
    private static final long serialVersionUID = 5689651039359180164L;

    @Schema(description = "账号 ID")
    @TableField
    private Long accountId;

    @Schema(description = "数据包")
    @TableField
    private String packet;

}
