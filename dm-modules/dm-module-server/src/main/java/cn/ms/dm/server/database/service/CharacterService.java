package cn.ms.dm.server.database.service;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.domain.Pair;
import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.domain.account.Characters;
import cn.ms.dm.domain.account.CharactersSlot;
import cn.ms.dm.domain.inventory.ItemInventory;
import cn.ms.dm.domain.inventory.ItemInventorySlot;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleGuildCharacter;
import cn.ms.dm.server.client.MaplePlayerStatus;
import cn.ms.dm.server.database.mapper.CharactersMapper;
import cn.ms.dm.server.database.mapper.CharactersSlotMapper;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.WzOperation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * @author LouMT
 * @name CharacterService
 * @date 2026-03-04 13:50
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class CharacterService extends ServiceImpl<CharactersMapper, Characters> {
    @Resource
    private ItemInventorySlotService itemInventorySlotService;
    @Resource
    private CharactersSlotService charactersSlotService;
    @Resource
    private ItemInventoryService itemInventoryService;

    public boolean isFullCharactersSlot(Long accountId, Integer world){
        LambdaQueryWrapper<CharactersSlot> wq = Wrappers.lambdaQuery(CharactersSlot.class)
                .eq(CharactersSlot::getAccountId, accountId)
                .eq(CharactersSlot::getWorld, world);
        long slotCount = charactersSlotService.count(wq);

        LambdaQueryWrapper<Characters> cwq = Wrappers.lambdaQuery(Characters.class)
                .eq(Characters::getAccountId, accountId)
                .eq(Characters::getWorld, world);
        long count = count(cwq);
        return count > slotCount;
    }

    public List<MapleCharacter> listMapleCharacter(Long accountId, Integer world) {
        LambdaQueryWrapper<Characters> wq = Wrappers.lambdaQuery(Characters.class)
                .eq(Characters::getAccountId, accountId)
                .eq(Characters::getWorld, world);
        List<Characters> characters = list(wq);
        return characters.stream().map(this::fillMapleCharacter).toList();
    }

    /**
     * 填充角色信息-方法1
     */
    public MapleCharacter fillMapleCharacter(Characters cha) {
        return fillMapleCharacter(cha.getId());
    }

    /**
     * 填充角色信息-方法2
     */
    public MapleCharacter fillMapleCharacter(Long characterId) {
        Characters character = getById(characterId);

        final MapleCharacter rs = new MapleCharacter();
        //初始化
        rs.setStance(0);
        rs.setPosition(new Point(0, 0));
        //物品
        rs.initInventory();

        //基础数据
        rs.setCharacterId(characterId.intValue());
        rs.setName(character.getName());
        rs.setLevel(character.getLevel());
        rs.setFame(character.getFame());
        rs.setExp(character.getExp());
        rs.setJob(character.getJob());
        rs.setRemainingAp(character.getAp());

        //剩余技能点
        Integer[] sps = new Integer[10];
        String[] sp = character.getSp().split(",");
        for (int i = 0; i < sp.length; i++) {
            sps[i] = StringUtil.isEmpty(sp[i]) ? 0 : Integer.parseInt(sp[i]);
        }
        rs.setRemainingSp(sps);

        rs.setHair(character.getHair());
        rs.setFace(character.getFace());
        rs.setMapId(character.getMapId());
        rs.setGender(character.getGender());
        rs.setSpawnPoint(character.getSpawnPoint());

        //状态数据
        MaplePlayerStatus status = new MaplePlayerStatus();
        status.setStrength(character.getStrength());
        status.setDexterity(character.getDexterity());
        status.setIntelligence(character.getIntelligence());
        status.setLuck(character.getLuck());
        status.setHp(character.getHp());
        status.setMaxHp(character.getMaxHp());
        status.setMp(character.getMp());
        status.setMaxMp(character.getMaxMp());
        rs.setStatus(status);

        //家族
        Long guildId = character.getGuildId();
        if (ObjectUtil.isNotNull(guildId)) {
            MapleGuildCharacter mapleGuildCharacter = new MapleGuildCharacter();
            mapleGuildCharacter.setId(character.getId().intValue());
            mapleGuildCharacter.setName(character.getName());
            mapleGuildCharacter.setLevel(character.getLevel());
            rs.setMgc(mapleGuildCharacter);
        }

        //组队
        rs.setParty(WorldOperation.Party.getParty(character.getPartyId()));

        //物品
        ItemInventorySlot itemInventorySlot = itemInventorySlotService.getOne(Wrappers.lambdaQuery(ItemInventorySlot.class).eq(ItemInventorySlot::getCharacterId, characterId));
        rs.setInventorySlot(MapleInventoryType.EQUIP, itemInventorySlot.getEquip());
        rs.setInventorySlot(MapleInventoryType.USE, itemInventorySlot.getUse());
        rs.setInventorySlot(MapleInventoryType.SETUP, itemInventorySlot.getSetup());
        rs.setInventorySlot(MapleInventoryType.ETC, itemInventorySlot.getEtc());
        rs.setInventorySlot(MapleInventoryType.CASH, itemInventorySlot.getCash());

        //物品
        Map<Integer, Pair<IMapleItem, MapleInventoryType>> equippedItems = WorldOperation.Item.loadEquipmentItems(characterId);
        for (Pair<IMapleItem, MapleInventoryType> mit : equippedItems.values()) {
            rs.getInventory(mit.getRight()).addItem(mit.getLeft());
        }
        return rs;
    }


    /**
     * 获取默认角色
     * 用于新建角色
     */
    public MapleCharacter getDefaultMapleCharacters(MapleClient client) {
        MapleCharacter characters = new MapleCharacter();
        //初始化
        characters.setStance(0);
        characters.setPosition(new Point(0, 0));
        //物品
        characters.initInventory();

        characters.setExp(0L);
        characters.setLevel(0);
        characters.setWorld(client.getWorld());

        //状态数据
        MaplePlayerStatus status = new MaplePlayerStatus();
        status.setStrength(12);
        status.setDexterity(5);
        status.setIntelligence(4);
        status.setLuck(4);
        status.setHp(50);
        status.setMaxHp(50);
        status.setMp(50);
        status.setMaxMp(50);
        characters.setStatus(status);
        return characters;
    }

    /**
     * 更新个人信息
     */
    public void changeGender(Long id, Gender gender) {
        this.update(Wrappers.lambdaUpdate(Characters.class).eq(Characters::getId, id).set(Characters::getGender, gender));
    }

    /**
     * 删除角色
     */
    public void deleteCharacters(Long characterId) {
        log.info("删除角色: {}", characterId);
        //删除角色
        removeById(characterId);
        //删除装备数据
        itemInventoryService.deleteCharacters(characterId);
        itemInventorySlotService.deleteCharacters(characterId);
        //删除公会数据
        WorldOperation.Guild.leave(characterId.intValue());
    }
}
