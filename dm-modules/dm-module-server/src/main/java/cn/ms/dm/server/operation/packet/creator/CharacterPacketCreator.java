package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.maple.constant.account.CharacterStat;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import cn.ms.dm.server.client.MapleCharacter;
import com.google.common.collect.Maps;

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
        return updateStats(new EnumMap<>(CharacterStat.class), true);
    }

    public static byte[] updateStats(final CharacterStat stat,final Integer val, final boolean itemReaction){
        Map<CharacterStat, Integer> statup = new EnumMap<>(CharacterStat.class);
        statup.put(stat, val);
        return updateStats(statup, itemReaction);
    }

    public static byte[] updateStats(Map<CharacterStat, Integer> mystats, final boolean itemReaction) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.UPDATE_STATS.getValue());
        //这个是什么状态
        mplew.write(itemReaction ? 1 : 0);

        //利用位运算（按位或）将多个状态码合并成一个单一的整数掩码
        int updateMask = 0;
        for (CharacterStat statupdate : mystats.keySet()) {
            updateMask |= statupdate.getCode();
        }
        mplew.writeInt(updateMask);
        for (final Map.Entry<CharacterStat, Integer> statupdate : mystats.entrySet()) {
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
                case MAX_HP:
                case MP:
                case MAX_MP:
                case AVAILABLE_AP:
                case FAME:
                    mplew.writeShort((statupdate.getValue()).shortValue());
                    break;
                case AVAILABLE_SP:
                    mplew.writeShort(statupdate.getValue());
                    break;
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
