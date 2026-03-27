package cn.ms.dm.domain.alliance.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name AllianceGuildVO
 * @date 2026-03-25 9:42
 * @email lmtemail163@163.com
 * @description
 */
@Data
@Schema(description = "联盟公会VO")
public class AllianceGuildVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -594315944157150486L;

    @Schema(description = "公会ID")
    private Long guildId;

    @Schema(description = "公会名")
    private String name;

    @Schema(description = "加入时间")
    private LocalDateTime joinTime;
}
