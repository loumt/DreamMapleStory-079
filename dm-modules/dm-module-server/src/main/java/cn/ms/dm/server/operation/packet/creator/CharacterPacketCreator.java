package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.maple.constant.MapleStat;
import cn.ms.dm.maple.constant.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author LouMT
 * @name CharacterPacketCreator
 * @date 2026-03-03 15:10
 * @email lmtemail163@163.com
 * @description
 */
public final class CharacterPacketCreator {
    public static byte[] enableActions() {
        return updatePlayerStats(new EnumMap<>(MapleStat.class), true);
    }

    public static byte[] updatePlayerStats(Map<MapleStat, Integer> mystats, final boolean itemReaction) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        mplew.write(itemReaction ? 1 : 0);

        int updateMask = 0;
        for (MapleStat statupdate : mystats.keySet()) {
            updateMask |= statupdate.getValue();
        }
        mplew.writeInt(updateMask);
        for (final Map.Entry<MapleStat, Integer> statupdate : mystats.entrySet()) {
            switch (statupdate.getKey()) {
                case SKIN:
                case LEVEL:
                    mplew.write((statupdate.getValue()).byteValue());
                    break;
                case JOB:
                case STR:
                case DEX:
                case INT:
                case LUK:
                case HP:
                case MAXHP:
                case MP:
                case MAXMP:
                case AVAILABLEAP:
                case FAME:
                    mplew.writeShort((statupdate.getValue()).shortValue());
                    break;
                case AVAILABLESP:
                case EXP:
                case FACE:
                case HAIR:
                case MESO:
                    mplew.writeInt((statupdate.getValue()));
                    break;
                case PET:
                    mplew.writeLong((statupdate.getValue()).longValue());
                    mplew.writeLong((statupdate.getValue()).longValue());
                    mplew.writeLong((statupdate.getValue()).longValue());
                    break;
                default:
                    mplew.writeInt((statupdate.getValue()));
                    break;
            }
        }
        if ((updateMask == 0L) && (!itemReaction)) {
            mplew.write(1);
        }

        mplew.write(0);
        mplew.write(0);
        return mplew.getPacket();
    }

}
