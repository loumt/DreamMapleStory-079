package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MaplePetExecutor
 * @date 2026-02-26 14:11
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][PET]")
@Component
public class PetOpExecutor {

    @PacketHandler(ReceivePacketOpcode.SPAWN_PET)
    public void handleSpawnPet(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MOVE_PET)
    public void handleMovePet(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_CHAT)
    public void handlePetChat(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_COMMAND)
    public void handlePetCommand(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_FOOD)
    public void handlePetFood(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_LOOT)
    public void handlePetLoot(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_AUTO_POT)
    public void handlePetAutoPotion(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.PET_IGNORE)
    public void handlePetIgnore(LittleEndianAccessor slea, MapleClient client) {
    }
}
