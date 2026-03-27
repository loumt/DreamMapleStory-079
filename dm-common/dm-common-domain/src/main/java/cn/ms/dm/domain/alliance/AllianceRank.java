package cn.ms.dm.domain.alliance;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name AllianceRank
 * @date 2026-03-25 9:19
 * @email lmtemail163@163.com
 * @description 公会联盟中的职级变化，
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_alliance_rank")
@Schema(description = "联盟职级")
public class AllianceRank extends BaseModel {
    @Serial
    private static final long serialVersionUID = -7030742471347123456L;
    @TableField
    @Schema(description = "联盟ID")
    private Long allianceId;

    @TableField
    @Schema(description = "公会ID")
    private Long guildId;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    @Schema(description = "职级")
    private AllianceRankType rank;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
