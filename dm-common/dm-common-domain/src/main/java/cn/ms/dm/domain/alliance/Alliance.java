package cn.ms.dm.domain.alliance;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name alliances
 * @date 2026-03-03 15:50
 * @email lmtemail163@163.com
 * @description 联盟
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("dm_alliance")
@Schema(description = "联盟")
public class Alliance extends BaseModel {
    @Serial
    private static final long serialVersionUID = 8045865424050583094L;

    @TableId
    @Schema(description = "联盟ID")
    private Long id;

    @TableField
    @Schema(description = "联盟名")
    private String name;

    @TableField
    @Schema(description = "通告")
    private String notice;

    @TableField
    @Schema(description = "容量")
    private Integer capacity;


}
