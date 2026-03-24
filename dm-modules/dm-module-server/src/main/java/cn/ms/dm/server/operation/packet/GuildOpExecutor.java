package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleGuildExecutor
 * @date 2026-02-26 13:59
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][GUILD]")
@Component
public class GuildOpExecutor {
    @PacketHandler(ReceivePacketOpcode.GUILD_OPERATION)
    public void handleGuildOperation(LittleEndianAccessor slea, MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.DENY_GUILD_REQUEST)
    public void handleDenyGuildRequest(LittleEndianAccessor slea, MapleClient client) {

    }
}
