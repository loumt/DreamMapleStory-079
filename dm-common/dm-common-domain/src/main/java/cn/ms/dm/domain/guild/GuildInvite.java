package cn.ms.dm.domain.guild;

import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name GuildInvite
 * @date 2026-03-27 15:50
 * @email lmtemail163@163.com
 * @description 家族邀请
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_guild_invite")
@Schema(description = "家族邀请")
public class GuildInvite extends BaseModel {
    @Serial
    private static final long serialVersionUID = -6908614709481480325L;

    @TableField
    @Schema(description = "邀请人ID")
    private Long invitorId;

    @TableField
    @Schema(description = "家族ID")
    private Long guildId;

    @TableField
    @Schema(description = "被邀请玩家ID")
    private Long acceptorId;

    @TableField
    @Schema(description = "接收")
    private YesOrNo accept;

    @TableField
    @Schema(description = "接收时间")
    private LocalDateTime acceptTime;

    @TableField
    @Schema(description = "过期时间")
    private LocalDateTime expireTime;
}
