package cn.ms.dm.domain.family;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @name Family
 * @date 2026-03-04 14:38
 * @author LouMT
 * @email lmtemail163@163.com
 * @description 家族
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_family")
@Schema(description = "家族")
public class Family extends BaseModel {
    @Serial
    private static final long serialVersionUID = 3078120083749906116L;

    @TableField
    @Schema(description = "家族名")
    private String name;

    @TableField
    @Schema(description = "族长ID")
    private Long leaderId;

    @TableField
    @Schema(description = "家族公告")
    private String notice;

    @TableField
    @Schema(description = "人数容量")
    private Integer capacity;

}