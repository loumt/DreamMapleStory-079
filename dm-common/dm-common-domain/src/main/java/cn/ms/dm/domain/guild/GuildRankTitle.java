package cn.ms.dm.domain.guild;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name GuildRankTitle
 * @date 2026-03-30 16:57
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_guild_rank_title")
@Schema(description = "家族职级表")
public class GuildRankTitle extends BaseModel {
    @Serial
    private static final long serialVersionUID = -8644355897380340319L;
    @TableField
    @Schema(description = "家族ID")
    private Long guildId;

    @TableField
    @Schema(description = "职级")
    private GuildRankType rank;

    @TableField
    @Schema(description = "职级名称")
    private String title;


}
