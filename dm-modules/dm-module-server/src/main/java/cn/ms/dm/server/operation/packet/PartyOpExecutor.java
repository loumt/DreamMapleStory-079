package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MaplePartyExecutor
 * @date 2026-02-26 14:08
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][PARTY]")
public class PartyOpExecutor {
    @PacketHandler(ReceivePacketOpcode.PARTY_OPERATION)
    public void handlePartyOperatopn(LittleEndianAccessor slea, MapleClient client) {
    }


    @PacketHandler(ReceivePacketOpcode.DENY_PARTY_REQUEST)
    public void handleDenyPartyRequest(LittleEndianAccessor slea, MapleClient client) {
    }
}
