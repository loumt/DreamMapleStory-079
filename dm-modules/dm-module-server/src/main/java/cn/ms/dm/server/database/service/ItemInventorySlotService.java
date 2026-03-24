package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.inventory.ItemInventory;
import cn.ms.dm.domain.inventory.ItemInventorySlot;
import cn.ms.dm.server.database.mapper.ItemInventorySlotMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name ItemInventorySlotService
 * @date 2026-03-16 10:17
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class ItemInventorySlotService extends ServiceImpl<ItemInventorySlotMapper, ItemInventorySlot> {
    public void deleteCharacters(Long characterId) {
        remove(Wrappers.lambdaQuery(ItemInventorySlot.class).eq(ItemInventorySlot::getCharacterId, characterId));
    }
}
