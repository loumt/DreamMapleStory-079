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
 * @name ItemInventoryEquipment
 * @date 2026-03-13 16:23
 * @email lmtemail163@163.com
 * @description 物品清单-已装备
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("item_inventory_equipment")
@Schema(description = "物品清单已装备")
public class ItemInventoryEquipment extends BaseModel {
    @Serial
    private static final long serialVersionUID = 1842051114714534879L;
    @TableField
    @Schema(description = "物品ID")
    private String itemId;
    @TableField
    @Schema(description = "等级")
    private Integer level;
    @TableField
    @Schema(description = "力量")
    private Integer strength;
    @TableField
    @Schema(description = "敏捷")
    private Integer dexterity;
    @TableField
    @Schema(description = "运气")
    private Integer luck;
    @TableField
    @Schema(description = "智力")
    private Integer intelligence;
    @TableField
    @Schema(description = "HP")
    private Integer hp;
    @TableField
    @Schema(description = "MP")
    private Integer mp;
    @TableField
    @Schema(description = "单双手")
    private Integer hands;
    @TableField
    @Schema(description = "速度")
    private Integer speed;
    @TableField
    @Schema(description = "跳跃")
    private Integer jump;

}
