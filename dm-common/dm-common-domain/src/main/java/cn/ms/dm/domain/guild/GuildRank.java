package cn.ms.dm.domain.guild;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name GuildRank
 * @date 2026-03-24 14:48
 * @email lmtemail163@163.com
 * @description 公会职级
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_guild_rank")
@Schema(description = "家族职级")
public class GuildRank extends BaseModel {
    @Serial
    private static final long serialVersionUID = -6648567980965008226L;
    @TableField
    @Schema(description = "家族ID")
    private Long guildId;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    @Schema(description = "职级")
    private GuildRankType rank;

    @TableField(value = "join_time", fill = FieldFill.INSERT)
    @Schema(description = "加入时间")
    private LocalDateTime joinTime;
}
