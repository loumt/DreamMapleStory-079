package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleChatExecutor
 * @date 2026-02-26 13:54
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][CHAT]")
@Component
public class ChatOpExecutor {

    /**
     * 普通聊天
     */
    @PacketHandler(ReceivePacketOpcode.GENERAL_CHAT)
    public void handleGeneralChat(final LittleEndianAccessor slea, final MapleClient client) {
        MapleCharacter character = client.getCharacter();
        if (!character.isCanTalk()) {
            client.sendPacket(MessagePacketCreator.serverNotice(6, "在这个地方不能说话。"));
        } else {
            String content = slea.readMapleAsciiString();
            WorldOperation.Broadcast.send(character, content);
        }
    }

    /**
     * 特殊对话
     */
    @PacketHandler(ReceivePacketOpcode.PARTYCHAT)
    public void handleSpecialChat(final LittleEndianAccessor slea,final MapleClient client) {
        final int code = slea.readByte();
        final byte numRecipients = slea.readByte();
        if (numRecipients <= 0) {
            return;
        }
        int recipients[] = new int[numRecipients];

        for (byte i = 0; i < numRecipients; i++) {
            recipients[i] = slea.readInt();
        }
        final String content = slea.readMapleAsciiString();
        MapleCharacter character = client.getCharacter();
        if (!character.isCanTalk()) {
            client.sendPacket(MessagePacketCreator.serverNotice(6, "在这个地方不能说话。"));
            return;
        }

        ChatType chatType = ChatType.codeOf(code);
        switch (chatType){
            case BUDDY -> {}
            case PARTY -> {}
            case GUILD -> {}
            case ALLIANCE -> {}
            default -> {

            }
        }


    }

    @PacketHandler(ReceivePacketOpcode.WHISPER)
    public void handleWhisper(final LittleEndianAccessor slea,final  MapleClient client) {
    }

    @PacketHandler(ReceivePacketOpcode.MESSENGER)
    public void handleMessenger(final LittleEndianAccessor slea,final  MapleClient client) {
    }
}
