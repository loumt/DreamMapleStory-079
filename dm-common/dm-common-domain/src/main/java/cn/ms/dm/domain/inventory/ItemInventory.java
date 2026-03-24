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
 * @name ItemInventory
 * @date 2026-03-13 16:22
 * @email lmtemail163@163.com
 * @description 物品清单
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("item_inventory")
@Schema(description = "物品清单")
public class ItemInventory extends BaseModel {
    @Serial
    private static final long serialVersionUID = -2724511556917188499L;

    @TableField
    @Schema(description = "账号ID")
    private Long accountId;

    @TableField
    @Schema(description = "角色ID")
    private Long characterId;

    @TableField
    private Long packageId;

    @TableField
    @Schema(description = "物品ID")
    private String itemId;

    @TableField
    @Schema(description = "类别")
    private MapleInventoryType type;

    @TableField
    @Schema(description = "位置")
    private Integer position;
}
