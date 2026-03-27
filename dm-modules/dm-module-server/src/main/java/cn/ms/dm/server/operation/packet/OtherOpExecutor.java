package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name OtherExecutor
 * @date 2026-02-26 15:07
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][OTHER]")
public class OtherOpExecutor {

    /**
     * ping -> pong
     */
    @PacketHandler(ReceivePacketOpcode.PONG)
    public void handlePong(MapleClient client) {
        client.pong();
    }

    @PacketHandler(ReceivePacketOpcode.CLIENT_FEEDBACK)
    public void handleFeedback(LittleEndianAccessor slea) {

    }

    @PacketHandler(ReceivePacketOpcode.CLIENT_ERROR)
    public void handleClientError(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.LICENSE_REQUEST)
    public void handleLicenseRequest(LittleEndianAccessor slea, MapleClient client) {

    }
}
