/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.core.enums.ResultEnum;
import cn.ms.dm.maple.constant.MapleConstant;
import cn.ms.dm.maple.constant.MapleWorld;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;
import cn.ms.dm.maple.utils.HexUtil;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;

import java.util.List;

/**
 * @author LouMT
 * @name MessagePacket
 * @date 2026-02-08 14:38
 * @email lmtemail163@163.com
 * @description 登录相关数据包构建工具
 */
public final class LoginPacketCreator {

    public static byte[] getHello(final short mapleVersion, final byte[] sendIv, final byte[] recvIv) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(16);
        // 13 = MSEA, 14 = GlobalMS, 15 = EMS
        mplew.writeShort(14);
        mplew.writeShort(mapleVersion);
        mplew.writeMapleAsciiString(MapleConstant.MAPLE_PATCH);
        mplew.write(recvIv);
        mplew.write(sendIv);
        // 7 = MSEA, 8 = GlobalMS, 5 = Test Server
        mplew.write(4);
        return mplew.getPacket();
    }

    public static byte[] getPing() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(16);
        mplew.writeShort(SendPacketOpcode.PING.getValue());
        return mplew.getPacket();
    }

    public static byte[] StrangeDATA() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(16);

        mplew.writeShort(0x12);
        // long string = generated static public key
        mplew.writeMapleAsciiString("30819F300D06092A864886F70D010101050003818D0030818902818100994F4E66B003A7843C944E67BE4375203DAA203C676908E59839C9BADE95F53E848AAFE61DB9C09E80F48675CA2696F4E897B7F18CCB6398D221C4EC5823D11CA1FB9764A78F84711B8B6FCA9F01B171A51EC66C02CDA9308887CEE8E59C4FF0B146BF71F697EB11EDCEBFCE02FB0101A7076A3FEB64F6F6022C8417EB6B87270203010001");
        //mplew.writeMapleAsciiString("30819D300D06092A864886F70D010101050003818B00308187028181009E68DD55B554E5924BA42CCB2760C30236B66234AFAA420E8E300E74F1FDF27CD22B7FF323C324E714E143D71780C1982E6453AD87749F33E540DB44E9F8C627E6898F915587CD2A7D268471E002D30DF2E214E2774B4D3C58609155A7C79E517CEA332AF96C0161BFF6EDCF1CB44BA21392BED48CBF4BD1622517C6EA788D8D020111");

        return mplew.getPacket();
    }

    /**
     * 构建登录失败数据包
     */
    public static byte[] getLoginFailed(final ResultEnum r) {
        final MaplePacketLittleEndianWriter mw = new MaplePacketLittleEndianWriter(16);
        mw.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());

        switch (r){
            //3: ID deleted or blocked
            case LOGIN_PWD_ERROR -> mw.write(3);
            //4: Incorrect password
            //5: Not a registered id
            case LOGIN_NO_ACCOUNT -> mw.write(5);
            //6: System error
            //7: Already logged in
            case LOGIN_NO_CHANNEL_SERVER -> mw.write(7);
            //8|9: System error
//            10: Cannot process so many connections
//            11: Only users older than 20 can use this channel
//            13: Unable to log on as master at this ip
//            14: Wrong gateway or personal info and weird korean button
//            15: Processing request with that korean button!
//            16: Please verify your account through email...
//            17: Wrong gateway or personal info
//            21: Please verify your account through email...
//            23: License agreement
//            25: Maple Europe notice
//            27: Some weird full client notice, probably for trial versions
//            32: IP blocked
//            84: please revisit website for pass change --> 0x07 recv with response 00/01*/
            default -> {}
        }
        mw.writeShort(0);
        return mw.getPacket();
    }


    public static byte[] getAuthSuccessRequest(final Integer accountId, final String accountName, final Gender gender) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        mplew.write(0);
        mplew.writeInt(accountId);
        mplew.write(gender.getCode());
        mplew.write(0); // Admin byte - Commands
        mplew.write(0); // Admin byte - Commands
        mplew.writeMapleAsciiString(accountName);
        mplew.write(HexUtil.getByteArrayFromHexString("00 00 00 03 01 00 00 00 E2 ED A3 7A FA C9 01"));
        mplew.write(0);
        mplew.writeLong(0);
        mplew.writeShort(0); //writeMapleAsciiString  CInPacket::DecodeStr
        mplew.write(0);
        mplew.writeMapleAsciiString(String.valueOf(accountId.intValue()));
        mplew.writeMapleAsciiString(accountName);
        mplew.write(1);

        return mplew.getPacket();
    }

    public static byte[] getServerList(final String serverName,
                                             final String serverNotice,
                                             final Integer channelCount,
                                             final MapleWorld world
    ) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.SERVERLIST.getValue());
        // 0 = Aquilla, 1 = bootes, 2 = cass, 3 = delphinus
        mplew.write(world.getWorld());
        mplew.writeMapleAsciiString(serverName);
        mplew.write(3);     //这个是什么我也不知道
        mplew.writeMapleAsciiString(serverNotice);
        mplew.writeShort(100);
        mplew.writeShort(100);
        mplew.write(channelCount);
        mplew.writeInt(300);
        int load = 1200;
        for (int i = 1; i <= channelCount; i++) {
            mplew.writeMapleAsciiString(world.name() + "-" + i);
            mplew.writeInt(Math.max(load * 55 / 20, 1));
            mplew.write(world.getWorld());
            mplew.writeShort(i - 1);
        }
        mplew.writeShort(0);
        /* mplew.writeShort(GameConstants.getBalloons().size());
        for (Balloon balloon : GameConstants.getBalloons()) {
            mplew.writeShort(balloon.nX);
            mplew.writeShort(balloon.nY);
            mplew.writeMapleAsciiString(balloon.sMessage);
        }*/
        return mplew.getPacket();
    }

    public static byte[] getEndOfServerList() {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.SERVERLIST.getValue());
        mplew.write(0xFF);

        return mplew.getPacket();
    }

    public static byte[] getPermBan(final byte reason) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(16);

        mplew.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        mplew.writeShort(2); // Account is banned
        mplew.write(0);
        mplew.write(reason);
        mplew.write(HexUtil.getByteArrayFromHexString("01 01 01 01 00"));

        return mplew.getPacket();
    }

    public static byte[] getTempBan(final long timestampTill, final byte reason) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter(17);

        mplew.writeShort(SendPacketOpcode.LOGIN_STATUS.getValue());
        mplew.write(2);
        mplew.write(HexUtil.getByteArrayFromHexString("00 00 00 00 00"));
        mplew.write(reason);
        mplew.writeLong(timestampTill); // Tempban date is handled as a 64-bit long, number of 100NS intervals since 1/1/1601. Lulz.

        return mplew.getPacket();
    }

    public static byte[] licenseResult() {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.LICENSE_RESULT.getValue());
        mplew.write(1);
        return mplew.getPacket();
    }

    public static byte[] getCharacterList(final List<MapleCharacter> chars) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.CHARLIST.getValue());
        mplew.write(0);
        mplew.writeInt(0);
        mplew.write(chars.size());
        for (final MapleCharacter chr : chars) {
            addCharEntry(mplew, chr);
        }
        // second pw request
        mplew.writeShort(3);
        mplew.writeInt(3);
        return mplew.getPacket();
    }


    public static byte[] addNewCharacter(final MapleCharacter chr, final boolean worked) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.ADD_NEW_CHAR_ENTRY.getValue());
        mplew.write(worked ? 0 : 1);
        addCharEntry(mplew, chr);
        return mplew.getPacket();
    }


    private static void addCharEntry(final MaplePacketLittleEndianWriter mplew, final MapleCharacter chr) {
        MaplePacketCreator.addCharStats(mplew, chr);
        MaplePacketCreator.addCharLook(mplew, chr, true);
    }


    public static byte[] charNameResponse(final String charname, final boolean nameUsed) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        mplew.writeShort(SendPacketOpcode.CHAR_NAME_RESPONSE.getValue());
        mplew.writeMapleAsciiString(charname);
        mplew.write(nameUsed ? 1 : 0);

        return mplew.getPacket();
    }

    public static byte[] genderChanged(MapleClient client) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.GENDER_SET.getValue());
        mplew.write(0);
        mplew.writeMapleAsciiString(client.getAccountName());
        mplew.writeMapleAsciiString(String.valueOf(client.getAccountId()));
        return mplew.getPacket();
    }

    public static byte[] deleteCharacterResponse(final int cid, final int state) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.DELETE_CHAR_RESPONSE.getValue());
        mplew.writeInt(cid);
        mplew.write(state);
        return mplew.getPacket();
    }


    public static byte[] getServerStatus(final int status) {
        final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        /*	 * 0 - Normal
         * 1 - Highly populated
         * 2 - Full*/
        mplew.writeShort(SendPacketOpcode.SERVERSTATUS.getValue());
        mplew.writeShort(status);
        return mplew.getPacket();
    }
}
