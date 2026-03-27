package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.domain.alliance.AllianceRank;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.client.MapleGuildCharacter;
import cn.ms.dm.server.operation.WorldOperation;

import java.util.List;

/**
 * @author LouMT
 * @name GuildPacketCreator
 * @date 2026-03-27 9:16
 * @email lmtemail163@163.com
 * @description 家族
 */
public final class GuildPacketCreator {

    public static byte[] genericGuildMessage(byte code) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.GUILD_OPERATION.getValue());
        mplew.write(code);
        return mplew.getPacket();
    }

    public static byte[] showGuildInfo() {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.GUILD_OPERATION.getValue());
        mplew.write(0x1A);
        mplew.write(0);
        return mplew.getPacket();
    }

    public static byte[] showGuildInfo(final MapleCharacter player) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.GUILD_OPERATION.getValue());
        mplew.write(0x1A);

        if (player == null || player.getMgc() == null) {
            mplew.write(0);
            return mplew.getPacket();
        }
        MapleGuild g = WorldOperation.Guild.getGuild(player.getGuildId());
        if (g == null) {
            mplew.write(0);
            return mplew.getPacket();
        }
        mplew.write(1);
        getGuildInfo(mplew, g);
        return mplew.getPacket();
    }

    public static void getGuildInfo(MaplePacketLittleEndianWriter mplew, MapleGuild guild) {
        //String Prefix = c == null ? "" : c.getNick();
        mplew.writeInt(guild.getId());
        mplew.writeMapleAsciiString(guild.getName() /*+ Prefix*/);

        //rank title
        mplew.writeMapleAsciiString(GuildRankType.LEADER.getType());
        mplew.writeMapleAsciiString(GuildRankType.VICE_LEADER.getType());
        mplew.writeMapleAsciiString(GuildRankType.ELDER.getType());
        mplew.writeMapleAsciiString(GuildRankType.ELITE.getType());
        mplew.writeMapleAsciiString(GuildRankType.MEMBER.getType());
        //添加成员信息
        addMembers(guild.getId(), mplew);

        //??????
        mplew.writeInt(guild.getCapacity());
//        mplew.writeShort(guild.getLogoBG());
        mplew.writeShort(0);

        //??????
//        mplew.write(guild.getLogoBGColor());
        mplew.write(0);

        //??????
//        mplew.writeShort(guild.getLogo());
        mplew.writeShort(0);

        //??????
//        mplew.write(guild.getLogoColor());
        mplew.write(0);

        mplew.writeMapleAsciiString(guild.getNotice());

        //??????
//        mplew.writeInt(guild.getGP());
        mplew.writeInt(0);
        mplew.writeInt(guild.getAllianceId() != null ? guild.getAllianceId() : 0);
    }

    private static void addMembers(Integer guildId, MaplePacketLittleEndianWriter mplew) {
        List<MapleGuildCharacter> members = WorldOperation.Guild.getMembers(guildId);
        mplew.write(members.size());

        for (final MapleGuildCharacter mgc : members) {
            mplew.writeInt(mgc.getId());
        }
        for (final MapleGuildCharacter mgc : members) {
            mplew.writeAsciiString(mgc.getName(), 13);
            mplew.writeInt(mgc.getJob());
            mplew.writeInt(mgc.getLevel());
            mplew.writeInt(mgc.getRank().getCode());
            mplew.writeInt(mgc.isOnline() ? 1 : 0);

            //??????
            mplew.writeInt(0);
//            mplew.writeInt(signature);

//            mplew.writeInt(mgc.getAllianceRank());
            mplew.writeInt(AllianceRankType.MEMBER.getCode());
        }
    }

    public static byte[] guildInvite(Integer guildId, String playerName) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.GUILD_OPERATION.getValue());
        mplew.write(0x05);
        mplew.writeInt(guildId);
        mplew.writeMapleAsciiString(playerName);
//        mplew.writeInt(levelFrom);
//        mplew.writeInt(jobFrom);

        return mplew.getPacket();
    }
}
