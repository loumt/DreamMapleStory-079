package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import cn.ms.dm.server.client.MapleAlliance;
import cn.ms.dm.server.client.MapleAllianceGuild;
import cn.ms.dm.server.client.MapleCharacter;

/**
 * @author LouMT
 * @name AlliancePacketCreator
 * @date 2026-03-24 16:53
 * @email lmtemail163@163.com
 * @description 联盟
 */
public final class AlliancePacketCreator {

    public static byte[] getAllianceUpdate(MapleAlliance alliance) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x17);
        addAllianceInfo(mplew, alliance);
        return mplew.getPacket();
    }


    private static void addAllianceInfo(MaplePacketLittleEndianWriter mplew, MapleAlliance alliance) {
        mplew.writeInt(alliance.getId());
        mplew.writeMapleAsciiString(alliance.getName());

        for (AllianceRankType rank : AllianceRankType.values()) {
            mplew.writeMapleAsciiString(alliance.getRank(rank));
        }
        mplew.write(alliance.getGuilds().size());
        for (MapleAllianceGuild guild : alliance.getGuilds().values()) {
            mplew.writeInt(guild.getId());
        }
        mplew.writeInt(alliance.getCapacity());
        mplew.writeMapleAsciiString(alliance.getNotice());
    }

    public static byte[] sendAllianceInvite(String allianceName, MapleCharacter character) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.ALLIANCE_OPERATION.getValue());
        mplew.write(0x03);
        mplew.writeInt(character.getGuildId());
        mplew.writeMapleAsciiString(character.getName());
        //alliance invite did NOT change
        mplew.writeMapleAsciiString(allianceName);
        return mplew.getPacket();
    }
}
