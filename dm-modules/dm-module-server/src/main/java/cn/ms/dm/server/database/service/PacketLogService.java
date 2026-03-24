package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.log.PacketLog;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.database.mapper.PacketLogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name PacketLogService
 * @date 2026-02-27 9:05
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class PacketLogService  extends ServiceImpl<PacketLogMapper, PacketLog> {

    @Async
    public void save(final LittleEndianAccessor slea,final MapleClient client) {
        PacketLog packetLog = new PacketLog();
        packetLog.setAccountId(client.getAccountId().longValue());
        packetLog.setPacket(slea.toString(true));
        this.save(packetLog);
    }
}
