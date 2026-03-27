package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleNPCExecutor
 * @date 2026-02-26 13:50
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][NPC]")
public class NPCOpExecutor {
    @PacketHandler(ReceivePacketOpcode.QUEST_ACTION)
    public void handleQuestAction(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.NPC_SHOP)
    public void handleNPCShop(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.NPC_TALK)
    public void handleNPCTalk(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.NPC_TALK_MORE)
    public void handleNPCMoreTalk(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.NPC_ACTION)
    public void handleNPCAnimation(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.STORAGE)
    public void handleStorage(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.REPAIR)
    public void handleRepair(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.REPAIR_ALL)
    public void handleRepairAll(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.UPDATE_QUEST)
    public void handleUpdateQuest(LittleEndianAccessor slea, MapleClient client) {
    }
    @PacketHandler(ReceivePacketOpcode.USE_ITEM_QUEST)
    public void handleUseItemQuest(LittleEndianAccessor slea, MapleClient client) {
    }
}
