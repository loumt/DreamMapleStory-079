package cn.ms.dm.center.client;

import cn.ms.dm.maple.netty.PacketCrypto;
import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LouMT
 * @name MapleClient
 * @date 2025-08-18 14:51
 * @email lmtemail163@163.com
 * @description
 */
@Data
public class MapleClient {
    private PacketCrypto sendCrypto, receiveCrypto;

    /**
     * 数据包串行锁
     */
    private final transient Lock packetLock = new ReentrantLock(true);

}
