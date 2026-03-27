package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleBBSExecutor
 * @date 2026-02-26 14:07
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][BBS]")
@Component
public class BBSOpExecutor {
    @PacketHandler(ReceivePacketOpcode.BBS_OPERATION)
    public void handleBBSOperation(LittleEndianAccessor slea, MapleClient client) {

    }
}
