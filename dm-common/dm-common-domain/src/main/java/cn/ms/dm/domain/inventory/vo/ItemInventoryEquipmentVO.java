package cn.ms.dm.domain.inventory.vo;

import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name ItemInventoryEquipmentVO
 * @date 2026-03-16 13:38
 * @email lmtemail163@163.com
 * @description
 */
@Data
public class ItemInventoryEquipmentVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 5520138033550778947L;

    @Schema(description = "物品ID")
    private Integer itemId;

    @Schema(description = "位置")
    private Integer position;

    @Schema(description = "类型")
    private MapleInventoryType type;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "力量")
    private Integer strength;

    @Schema(description = "敏捷")
    private Integer dexterity;

    @Schema(description = "运气")
    private Integer luck;

    @Schema(description = "智力")
    private Integer intelligence;

    @Schema(description = "HP")
    private Integer hp;

    @Schema(description = "MP")
    private Integer mp;

    @Schema(description = "单双手")
    private Integer hands;

    @Schema(description = "速度")
    private Integer speed;

    @Schema(description = "跳跃")
    private Integer jump;


}
