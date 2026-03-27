package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleInventory;
import lombok.SneakyThrows;

import java.net.InetAddress;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LouMT
 * @name PacketHelper
 * @date 2026-03-10 9:08
 * @email lmtemail163@163.com
 * @description 共用数据包构建工具
 */
public final class MaplePacketCreator {
    private final static long FT_UT_OFFSET = 116444592000000000L; // EDT
    public final static long MAX_TIME = 150842304000000000L; //00 80 05 BB 46 E6 17 02
    public final static long ZERO_TIME = 94354848000000000L; //00 40 E0 FD 3B 37 4F 01
    public final static long PERMANENT = 150841440000000000L; // 00 C0 9B 90 7D E5 17 02

    public static long getTime(long realTimestamp) {
        if (realTimestamp == -1) {
            return MAX_TIME;
        } else if (realTimestamp == -2) {
            return ZERO_TIME;
        } else if (realTimestamp == -3) {
            return PERMANENT;
        }
        return ((realTimestamp * 10000) + FT_UT_OFFSET);
    }

    public static void addCharStats(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        mplew.writeInt(chr.getPlayerId().intValue());
        mplew.writeAsciiString(chr.getName(), 13);
        // gender (0 = male, 1 = female)
        mplew.write(chr.getGender().getCode());
        mplew.write(chr.getSkinColor());
        mplew.writeInt(chr.getFace());
        mplew.writeInt(chr.getHair());
        mplew.writeZeroBytes(24);
        mplew.write(chr.getLevel());
        mplew.writeShort(chr.getJob());

        mplew.writeShort(chr.getStatus().getStrength());
        mplew.writeShort(chr.getStatus().getDexterity());
        mplew.writeShort(chr.getStatus().getIntelligence());
        mplew.writeShort(chr.getStatus().getLuck());
        mplew.writeShort(chr.getStatus().getHp());
        mplew.writeShort(chr.getStatus().getMaxHp());
        mplew.writeShort(chr.getStatus().getMp());
        mplew.writeShort(chr.getStatus().getMaxMp());

        mplew.writeShort(chr.getRemainingAp());
        mplew.writeShort(chr.getRemainingSp());
        mplew.writeInt(chr.getExp().intValue());
        mplew.writeShort(chr.getFame());
        // Gachapon exp
        mplew.writeInt(0);
        mplew.writeLong(MaplePacketCreator.getTime(System.currentTimeMillis()));
        // current map id
        mplew.writeInt(chr.getMapId());
        // spawnpoint
        mplew.write(chr.getSpawnPoint());
    }

    public static void addCharLook(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr, final boolean mega) {
        mplew.write(chr.getGender().getCode());
        mplew.write(chr.getSkinColor());
        mplew.writeInt(chr.getFace());
        mplew.write(mega ? 0 : 1);
        mplew.writeInt(chr.getHair());

        final Map<Byte, Integer> myEquip = new LinkedHashMap<>();
        final Map<Byte, Integer> maskedEquip = new LinkedHashMap<>();

        //获取已穿戴的装备
        MapleInventory inventory = chr.getInventory(MapleInventoryType.EQUIPPED);
        Collection<IMapleItem> equippedItems = inventory.list();

        //TODO 获取装备显示在角色列表中， 分两种?
        for (final IMapleItem item : equippedItems) {
            if (item.getPosition() < -128) continue;

            byte pos = (byte) (item.getPosition() * -1);
            if (pos < 100 && myEquip.get(pos) == null) {
                myEquip.put(pos, item.getItemId());
            } else if ((pos > 100 || pos == -128) && pos != 111) {
                pos = (byte) (pos == -128 ? 28 : pos - 100);
                if (myEquip.get(pos) != null) {
                    maskedEquip.put(pos, myEquip.get(pos));
                }
                myEquip.put(pos, item.getItemId());
            } else if (myEquip.get(pos) != null) {
                maskedEquip.put(pos, item.getItemId());
            }
        }

        // visible items
        for (final Map.Entry<Byte, Integer> entry : myEquip.entrySet()) {
            mplew.write(entry.getKey());
            mplew.writeInt(entry.getValue());
        }
        mplew.write(0xFF);
        // end of visible items

        // masked items
        for (final Map.Entry<Byte, Integer> entry : maskedEquip.entrySet()) {
            mplew.write(entry.getKey());
            mplew.writeInt(entry.getValue());
        }
        mplew.write(0xFF);
        // ending markers

        //获取武器
        final IMapleItem cWeapon = inventory.getMapleItem(-111);
        mplew.writeInt(cWeapon != null ? cWeapon.getItemId() : 0);
        mplew.writeInt(0);
        mplew.writeLong(0);
    }



    @SneakyThrows
    public static byte[] getServerIP(final String ip, final int port, final int clientId) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.SERVER_IP.getValue());
        mplew.writeShort(0);

        byte[] ipBytes = InetAddress.getByName(ip).getAddress();
        mplew.write(ipBytes);
        mplew.writeShort(port);
        mplew.writeInt(clientId);
        mplew.write(new byte[]{1, 0, 0, 0, 0});
        return mplew.getPacket();
    }
}
