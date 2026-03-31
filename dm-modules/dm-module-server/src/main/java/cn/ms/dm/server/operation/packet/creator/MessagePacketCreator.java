package cn.ms.dm.server.operation.packet.creator;

import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.MaplePacketLittleEndianWriter;

/**
 * @author LouMT
 * @name MessagePacket
 * @date 2026-02-25 14:38
 * @email lmtemail163@163.com
 * @description 消息数据包构建工具
 */
public final class MessagePacketCreator {
    public static byte[] serverMessage(String message) {
        return serverMessage(4, 0, message, false);
    }

    public static byte[] serverNotice(int type, String message) {
        return serverMessage(type, 0, message, false);
    }

    public static byte[] serverNotice(int type, int channel, String message) {
        return serverMessage(type, channel, message, false);
    }

    public static byte[] serverNotice(int type, int channel, String message, boolean smegaEar) {
        return serverMessage(type, channel, message, smegaEar);
    }

    private static byte[] serverMessage(int type, int channel, String message, boolean megaEar) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();

        /*	* 0: [Notice]<br>
         * 1: Popup<br>
         * 2: Megaphone<br>
         * 3: Super Megaphone<br>
         * 4: Scrolling message at top<br>
         * 5: Pink Text<br>
         * 6: Lightblue Text
         * 8: Item megaphone
         * 9: Heart megaphone
         * 10: Skull Super megaphone
         * 11: Green megaphone message?
         * 12: Three line of megaphone text
         * 13: End of file =.="
         * 14: Green Gachapon box
         * 15: Red Gachapon box
         * 18: Blue Notice (again)*/
        mplew.writeShort(SendPacketOpcode.SERVERMESSAGE.getValue());
        mplew.write(type);
        if (type == 4) {
            mplew.write(1);
        }
        mplew.writeMapleAsciiString(message);

        switch (type) {
            case 3:
            case 9:
            case 10:
                mplew.write(channel - 1); // 频道
                mplew.write(megaEar ? 1 : 0); // 能否密語
                break;
            case 6:
            case 18:
                mplew.writeInt(channel >= 1000000 && channel < 6000000 ? channel : 0); //cash itemID, displayed in yellow by the {name}
                //E.G. All new EXP coupon {Ruby EXP Coupon} is now available in the Cash Shop!
                //with Ruby Exp Coupon being in yellow and with item info
                break;
        }
        return mplew.getPacket();
    }


    public static byte[] yellowChat(String msg) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.SET_WEEK_EVENT_MESSAGE.getValue());
        mplew.write(-1); //could be something like mob displaying message.
        mplew.writeMapleAsciiString(msg);
        return mplew.getPacket();
    }


    public static byte[] getChatText(Long characterId, String text, boolean whiteBG, int show) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.CHATTEXT.getValue());
        mplew.writeInt(characterId.intValue());
        mplew.write(whiteBG ? 1 : 0);
        mplew.writeMapleAsciiString(text);
        mplew.write(show);
        return mplew.getPacket();
    }


    /**
     * 发送聊天数据
     * @param senderName
     * @param content
     * @param mode //  0 buddychat; 1 partychat; 2 guildchat
     * @return
     */
    public static byte[] multiChat(String senderName, String content, ChatType mode) {
        MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
        mplew.writeShort(SendPacketOpcode.MULTICHAT.getValue());
        mplew.write(mode.getMode());
        mplew.writeMapleAsciiString(senderName);
        mplew.writeMapleAsciiString(content);
        return mplew.getPacket();
    }
}
