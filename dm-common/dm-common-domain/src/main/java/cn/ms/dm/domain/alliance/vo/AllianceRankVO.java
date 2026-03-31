package cn.ms.dm.domain.alliance.vo;

import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name AllianceRankVO
 * @date 2026-03-25 9:42
 * @email lmtemail163@163.com
 * @description
 */
@Data
@Schema(description = "联盟职级VO")
public class AllianceRankVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6816323433302228701L;

    @TableField
    @Schema(description = "职级")
    private AllianceRankType rank;

    @TableField
    @Schema(description = "职级名称")
    private String title;

}
