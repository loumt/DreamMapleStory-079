package cn.ms.dm.domain.shop;

import cn.ms.dm.domain.base.BaseModel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name ShopItems
 * @date 2026-01-30 16:40
 * @email lmtemail163@163.com
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "商店物品列表")
@TableName("sys_shop_items")
public class ShopItems extends BaseModel {
    @Serial
    private static final long serialVersionUID = -5053202413480408278L;

    @TableField
    @Schema(description = "商店ID")
    private Long shopId;

    @TableField
    @Schema(description = "物品ID")
    private Long itemId;

    @TableField
    @Schema(description = "价格")
    private Integer price;

    @TableField
    @Schema(description = "位置/序号")
    private Integer position;

}
