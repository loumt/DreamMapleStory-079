package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleMonsterExecutor
 * @date 2026-02-26 14:15
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][MONSTER]")
public class MonsterOpExecutor {

    @PacketHandler(ReceivePacketOpcode.MONSTER_CARNIVAL)
    public void handleMonsterCarnival(LittleEndianAccessor slea, MapleClient client) {
    }
}
