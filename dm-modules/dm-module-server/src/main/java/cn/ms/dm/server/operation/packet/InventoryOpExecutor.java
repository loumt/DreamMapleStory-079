package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleInventoryExecutor
 * @date 2026-02-26 13:46
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][Inventory]")
public class InventoryOpExecutor {

    @PacketHandler(ReceivePacketOpcode.ITEM_SORT)
    public void handleItemSort(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.ITEM_GATHER)
    public void handleItemGather(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.ITEM_MOVE)
    public void handleItemMove(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.ITEM_PICKUP)
    public void handleItemPickup(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_CASH_ITEM)
    public void handleUseCashItem(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_ITEM)
    public void handleUseItem(LittleEndianAccessor slea, MapleClient client) {
        
    }
    @PacketHandler(ReceivePacketOpcode.USE_MAGNIFY_GLASS)
    public void handleUseMagnifyClass(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_SCRIPTED_NPC_ITEM)
    public void handleUseScriptedNPCItem(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_RETURN_SCROLL)
    public void handleUseReturnScroll(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_UPGRADE_SCROLL)
    public void handleUseUpgradeScroll(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_SUMMON_BAG)
    public void handleUseSummonBag(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_TREASUER_CHEST)
    public void handleUseTreasureChest(LittleEndianAccessor slea, MapleClient client) {
        
    }
    @PacketHandler(ReceivePacketOpcode.USE_SKILL_BOOK)
    public void handleUseSkillBook(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_CATCH_ITEM)
    public void handleUseCatchItem(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.USE_MOUNT_FOOD)
    public void handleUseMountFood(LittleEndianAccessor slea, MapleClient client) {
        
    }

    @PacketHandler(ReceivePacketOpcode.REWARD_ITEM)
    public void handleRewardItem(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.OWL)
    public void handleOwl(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.OWL_WARP)
    public void handleOwlWarp(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.USE_OWL_MINERVA)
    public void handleUseOwlMinerva(LittleEndianAccessor slea, MapleClient client) {

    }
}
