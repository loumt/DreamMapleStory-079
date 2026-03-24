package cn.ms.dm.server.client;

import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LouMT
 * @name MapleItem
 * @date 2026-03-16 14:23
 * @email lmtemail163@163.com
 * @description 物品
 */
@Getter
@Setter
public class MapleItem implements IMapleItem {
    /**
     * 物品ID
     */
    private final Integer itemId;

    /**
     * 物品类型
     */
    private MapleInventoryType type;

    /**
     * 位置
     */
    private Integer position;

    //不允许出售
    private Integer notSale;
    //是否角色唯一
    private Integer only;
    //交易限制 0可交易 1不可交易
    private Integer tradeBlock;
    //堆叠最大数量
    private Integer slotMax;
    //时间限制
    private Integer timeLimited;
    //价格
    private Integer price;
    //是否为现金装备
    private Integer cash;

    public MapleItem(Integer itemId, Integer position) {
        this.itemId = itemId;
        this.position = position;
    }

    @Override
    public int compareTo(IMapleItem other) {
        if (Math.abs(position) < Math.abs(other.getPosition())) {
            return -1;
        } else if (Math.abs(position) == Math.abs(other.getPosition())) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public IMapleItem copy() {
        return null;
    }

}
