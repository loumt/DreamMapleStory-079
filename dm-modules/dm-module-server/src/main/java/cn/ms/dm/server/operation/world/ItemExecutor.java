package cn.ms.dm.server.operation.world;

import cn.ms.dm.core.domain.Pair;
import cn.ms.dm.domain.inventory.vo.ItemInventoryEquipmentVO;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.server.client.MapleEquip;
import cn.ms.dm.server.client.MapleInventory;
import cn.ms.dm.server.database.service.ItemInventoryEquipmentService;
import cn.ms.dm.server.database.service.ItemInventoryService;
import com.google.common.collect.Maps;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LouMT
 * @name ItemExecutor
 * @date 2026-03-13 15:46
 * @email lmtemail163@163.com
 * @description 物品清单
 */
@Component
@Slf4j(topic = "【物品清单】")
@RequiredArgsConstructor
public final class ItemExecutor {
    @Resource
    private ItemInventoryService itemInventoryService;

    public Map<Integer, Pair<IMapleItem, MapleInventoryType>> loadEquipmentItems(Long characterId){
        Map<Integer, Pair<IMapleItem, MapleInventoryType>> rs = Maps.newHashMap();

        List<ItemInventoryEquipmentVO> inventoryEquipments = itemInventoryService.loadEquip(characterId, MapleInventoryType.EQUIPPED);
        for (ItemInventoryEquipmentVO inventoryEquipment : inventoryEquipments) {
            MapleEquip equip = new MapleEquip(inventoryEquipment.getItemId(), inventoryEquipment.getPosition());
            rs.put(inventoryEquipment.getItemId(), new Pair<>(equip.copy(), inventoryEquipment.getType()));
        }
        return rs;
    }
}
