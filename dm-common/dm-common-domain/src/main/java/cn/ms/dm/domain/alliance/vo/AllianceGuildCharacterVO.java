package cn.ms.dm.domain.alliance.vo;

import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name AllianceGuildCharacterVO
 * @date 2026-03-31 14:48
 * @email lmtemail163@163.com
 * @description
 */
@Data
@Schema(description = "联盟成员VO")
public class AllianceGuildCharacterVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5147015856015845696L;

    @Schema(description = "公会ID")
    private Long guildId;

    @Schema(description = "玩家ID")
    private Long characterId;

    @Schema(description = "玩家名")
    private String name;

    @Schema(description = "职级")
    private AllianceRankType rank;
}
