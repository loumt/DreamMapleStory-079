package cn.ms.dm.maple.constant.inventory;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @description 加载物品列表的方式，用来便捷查询item数据的
 * @author LouMT
 * @date 2026/3/13 16:06
 */
@Getter
public enum MapleInventoryMethodType {
    INVENTORY("item_inventory", "item_inventory_equipment", 0, "characterId"),
    STORAGE("item_inventory", "item_inventory_equipment", 1, "accountId"),
    CASHSHOP_EXPLORER("item_cs", "item_cs_equipment", 2, "accountId"),
    CASHSHOP_CYGNUS("item_cs", "item_cs_equipment", 3, "accountId"),
    CASHSHOP_ARAN("item_cs", "item_cs_equipment", 4, "accountId"),
    HIRED_MERCHANT("item_hired_merch", "item_hired_merch_equipment", 5, "packageId", "accountId"),
    DUEY("item_duey", "item_duey_equipment", 6, "packageId"),
    CASH_SHOP_EVAN("item_cs", "item_cs_equipment", 7, "accountId"),
    MTS("item_mts", "item_mts_equipment", 8, "packageId");

    //表名
    private final String table;
    //已装备表名
    private final String tableEquipment;

    private final Integer value;
    //参数
    private final List<String> args;

    MapleInventoryMethodType(String table, String tableEquipment, Integer value, String... args) {
        this.table = table;
        this.tableEquipment = tableEquipment;
        this.value = value;
        this.args = Arrays.asList(args);
    }
}
