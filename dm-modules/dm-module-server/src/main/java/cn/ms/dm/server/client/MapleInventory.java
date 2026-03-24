package cn.ms.dm.server.client;

import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * @author LouMT
 * @name MapleInventory
 * @date 2026-03-13 11:26
 * @email lmtemail163@163.com
 * @description 个人物品清单
 */
@Getter
@Setter
public class MapleInventory implements Iterable<IMapleItem>, Serializable {
    @Serial
    private static final long serialVersionUID = 4957019240108760298L;
    /**
     * 物品类型
     */
    private final MapleInventoryType type;
    /**
     * 槽位数量
     * 例如:物品栏-装备栏-48格
     */
    private Integer slot;
    /**
     * 物品列表
     * key = position
     * value = 物品对象
     */
    private final Map<Integer, IMapleItem> inventory;


    public MapleInventory(MapleInventoryType type) {
        this.inventory = new LinkedHashMap<>();
        this.type = type;
    }

    public Collection<IMapleItem> list() {
        return inventory.values();
    }

    public IMapleItem getMapleItem(Integer position) {
        return inventory.get(position);
    }

    public void addItem(IMapleItem item) {
        this.inventory.put(item.getPosition(), item);
    }

    @Override
    public Iterator<IMapleItem> iterator() {
        return Collections.unmodifiableCollection(inventory.values()).iterator();
    }

}
