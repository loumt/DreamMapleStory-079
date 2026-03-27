package cn.ms.dm.domain.guild.vo;

import cn.ms.dm.maple.constant.guild.GuildRankType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name GuildCharacters
 * @date 2026-03-24 15:04
 * @email lmtemail163@163.com
 * @description 组员
 */
@Data
public class GuildCharacterVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2515114899884223005L;
    @Schema(description = "角色ID")
    private Long characterId;

    @Schema(description = "角色名")
    private String name;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "职级")
    private GuildRankType rank;

    @Schema(description = "职业")
    private Integer job;

}
