package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleInterExecutor
 * @date 2026-02-26 9:43
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][INTER]")
public class InterOpExecutor {
    /**
     * 变更频道
     */
    @PacketHandler(ReceivePacketOpcode.CHANGE_CHANNEL)
    public void handleChangeChannel(LittleEndianAccessor slea, MapleClient client) {

    }

    /**
     * 进入商城
     */
    @PacketHandler(ReceivePacketOpcode.ENTER_CASH_SHOP)
    public void handleEnterCashShop(LittleEndianAccessor slea, MapleClient client) {

    }
}
