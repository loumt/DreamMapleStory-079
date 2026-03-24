package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.inventory.ItemInventory;
import cn.ms.dm.domain.inventory.vo.ItemInventoryEquipmentVO;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.server.database.mapper.ItemInventoryMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LouMT
 * @name ItemInventoryService
 * @date 2026-03-13 16:40
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j
@Service
public class ItemInventoryService extends ServiceImpl<ItemInventoryMapper, ItemInventory> {
    /**
     * 加载已装备的物品
     */
    public List<ItemInventoryEquipmentVO> loadEquip(Long characterId, MapleInventoryType type) {
        return this.getBaseMapper().loadEquip(characterId, type);
    }

    public void deleteCharacters(Long characterId) {
        remove(Wrappers.lambdaQuery(ItemInventory.class).eq(ItemInventory::getCharacterId, characterId));
    }
}
