package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleDueyExecutor
 * @date 2026-02-26 14:17
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][DUEY]")
@Component
public class DueyOpExecutor {

    @PacketHandler(ReceivePacketOpcode.DUEY_ACTION)
    public void handleDueyOperation(LittleEndianAccessor slea, MapleClient client) {

    }
}
