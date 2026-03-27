package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleItemMakerExecutor
 * @date 2026-02-26 13:45
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][ITEM-MARKER]")
public class ItemMakerOpExecutor {
    @PacketHandler(ReceivePacketOpcode.ITEM_MAKER)
    public void handleItemMaker(LittleEndianAccessor slea, MapleClient client) {

    }
}
