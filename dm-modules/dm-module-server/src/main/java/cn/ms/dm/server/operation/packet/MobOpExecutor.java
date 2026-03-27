package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleMobExecutor
 * @date 2026-02-26 13:49
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][MOB]")
public class MobOpExecutor {
    @PacketHandler(ReceivePacketOpcode.HYPNOTIZE_DMG)
    public void handleHypnotizeDmg(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MOB_NODE)
    public void handleMobNode(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.DISPLAY_NODE)
    public void handleDisplayNode(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MOVE_LIFE)
    public void handleMoveMonster(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.AUTO_AGGRO)
    public void handleAutoAggro(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.FRIENDLY_DAMAGE)
    public void handleFriendlyDamage(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MONSTER_BOMB)
    public void handleMonsterBomb(LittleEndianAccessor slea, MapleClient client) {

    }
}
