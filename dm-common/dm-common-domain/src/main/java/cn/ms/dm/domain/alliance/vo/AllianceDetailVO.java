package cn.ms.dm.domain.alliance.vo;

import cn.ms.dm.domain.alliance.Alliance;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.util.List;

/**
 * @author LouMT
 * @name AllianceDetailVO
 * @date 2026-03-25 9:40
 * @email lmtemail163@163.com
 * @description 联盟详情
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "联盟详情VO")
public class AllianceDetailVO extends Alliance {
    @Serial
    private static final long serialVersionUID = -5562833542641393249L;

    /**
     * 公会
     */
    private List<AllianceGuildVO> guilds;

    /**
     * 职级
     */
    private List<AllianceRankVO> ranks;
}
