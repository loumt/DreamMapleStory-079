package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleSummonExecutor
 * @date 2026-02-26 14:11
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][SUMMON]")
public class SummonOpExecutor {

    @PacketHandler(ReceivePacketOpcode.DAMAGE_SUMMON)
    public void handleDamageSummon(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MOVE_SUMMON)
    public void handleMoveSummon(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.SUMMON_ATTACK)
    public void handleSummonAttack(LittleEndianAccessor slea, MapleClient client) {
    }
}
