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
 * @name BossLog
 * @date 2025-08-14 14:59
 * @email lmtemail163@163.com
 * @description 击杀BOSS日志
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "击杀BOSS日志")
@TableName("log_boss")
public class BossLog extends BaseLog {
    @Serial
    private static final long serialVersionUID = 9015997789577423256L;

    @TableField
    @Schema(description = "角色 ID")
    private Long characterId;

    @TableField
    @Schema(description = "BOSS ID")
    private Long bossId;


}
