package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleFamilyExecutor
 * @date 2026-02-26 14:24
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][FAMILY]")
@Component
public class FamilyOpExecutor {

    @PacketHandler(ReceivePacketOpcode.REQUEST_FAMILY)
    public void handleRequestFamily(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.OPEN_FAMILY)
    public void handleOpenFamily(LittleEndianAccessor slea, MapleClient client) {

    }
    @PacketHandler(ReceivePacketOpcode.FAMILY_OPERATION)
    public void handleFamilyOperation(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.DELETE_JUNIOR)
    public void handleDeleteJunior(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.DELETE_SENIOR)
    public void handleDeleteSenior(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.USE_FAMILY)
    public void handleUseFamily(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.FAMILY_PRECEPT)
    public void handleFamilyPrecept(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.FAMILY_SUMMON)
    public void handleFamilySummon(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.ACCEPT_FAMILY)
    public void handleAcceptFamily(LittleEndianAccessor slea, MapleClient client) {

    }
}
