package cn.ms.dm.domain.party;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.maple.constant.party.PartyRankType;
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
 * @name PartyRank
 * @date 2026-03-24 15:34
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_party_rank")
@Schema(description = "组队")
public class PartyRank extends BaseModel {
    @Serial
    private static final long serialVersionUID = 259816529326735020L;
    @TableField
    @Schema(description = "队伍ID")
    private Long partyId;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    @Schema(description = "职级")
    private PartyRankType rank;

    @TableField(value = "join_time", fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime joinTime;
}
