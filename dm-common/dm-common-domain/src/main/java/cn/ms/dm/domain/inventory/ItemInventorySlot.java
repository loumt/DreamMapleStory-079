package cn.ms.dm.domain.inventory;

import cn.ms.dm.domain.base.BaseModel;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * @author LouMT
 * @name ItemInventorySlot
 * @date 2026-03-16 10:18
 * @email lmtemail163@163.com
 * @description 物品清单插槽数(用于限制角色物品栏的数目)
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("item_inventory_slot")
@Schema(description = "物品清单插槽数")
public class ItemInventorySlot extends BaseModel {
    @Serial
    private static final long serialVersionUID = -2724511556917188499L;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    @Schema(description = "装备栏-栏位数")
    private Integer equip;


    @TableField
    @Schema(description = "消耗栏-栏位数")
    private Integer use;


    @TableField
    @Schema(description = "设置栏-栏位数")
    private Integer setup;


    @TableField
    @Schema(description = "其他栏-栏位数")
    private Integer etc;


    @TableField
    @Schema(description = "现金栏-栏位数")
    private Integer cash;
}
