package cn.ms.dm.server.operation.packet;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.MessengerMode;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.*;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.CharacterPacketCreator;
import cn.ms.dm.server.operation.packet.creator.MaplePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessengerPacketCreator;
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
        MapleCharacter character = client.getPlayer();
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
    @PacketHandler(ReceivePacketOpcode.PARTY_CHAT)
    public void handleSpecialChat(final LittleEndianAccessor slea, final MapleClient client) {
        //对话类型
        final int mode = slea.readByte();
        //接收人
        final byte numRecipients = slea.readByte();
        if (numRecipients <= 0) return;

        int recipients[] = new int[numRecipients];

        for (byte i = 0; i < numRecipients; i++) {
            recipients[i] = slea.readInt();
        }
        //消息内容
        final String content = slea.readMapleAsciiString();

        MapleCharacter character = client.getPlayer();
        if (!character.isCanTalk()) {
            client.sendPacket(MessagePacketCreator.serverNotice(6, "在这个地方不能说话。"));
            return;
        }

        ChatType chatType = ChatType.modeOf(mode);
        if(chatType == null) return;

        switch (chatType){
            case BUDDY:
                handleBuddyChatOperation(character, recipients, content);
                break;
            case PARTY:
                handlePartyChatOperation(character, content);
                break;
            case GUILD:
                handleGuildChatOperation(character, content);
                break;
            case ALLIANCE:
                handleAllianceChatOperation(character, content);
                break;
        }
    }

    /**
     * 秘语
     */
    @PacketHandler(ReceivePacketOpcode.WHISPER)
    public void handleWhisper(final LittleEndianAccessor slea, final MapleClient client) {
        final byte mode = slea.readByte();


    }


    /**
     * 喇叭
     */
    @PacketHandler(ReceivePacketOpcode.MESSENGER)
    public void handleMessenger(final LittleEndianAccessor slea, final MapleClient client) {
        MapleCharacter character = client.getPlayer();
        if (!character.isCanTalk()) {
            client.sendPacket(MessagePacketCreator.serverNotice(5, "目前喇叭停止使用。"));
            client.sendPacket(CharacterPacketCreator.enableActions());
            return;
        }

        MessengerMode mode = MessengerMode.modeOf(slea.readByte());
        if(mode == null) return;

        switch (mode) {
            case OPEN:
                handleOpenMessengerOperation(character, slea);
                break;
            case EXIT:
                handleExitMessengerOperation(character);
                break;
            case INVITE:
                handleInviteMessengerOperation(character, slea.readMapleAsciiString());
                break;
            case DECLINE:
                handleDeclineMessengerOperation(character, slea.readMapleAsciiString());
                break;
            case MESSAGE:
                handleMessageMessengerOperation(character, slea.readMapleAsciiString());
                break;
            default: log.error("Unhandled Messenger operation : " + mode);
        }
    }

    private void handleMessageMessengerOperation(MapleCharacter character, String content) {
        if(StringUtil.isEmpty(content)) return;
        if(character.getMessenger() == null) return;

        WorldOperation.Messenger.messengerChat(character.getMessenger().getId(), character.getName(), content);
    }

    private void handleDeclineMessengerOperation(MapleCharacter character, String targetName) {
        if(character.getMessenger() != null) return;
        if(StringUtil.isEmpty(targetName)) return;
        MapleCharacter targetCharacter = ChannelServerGroup.getCharacter(targetName);
        if(targetCharacter == null) return;
        targetCharacter.sendPacket(MessengerPacketCreator.messengerNote(character.getName(), 5, 0));
    }

    private void handleInviteMessengerOperation(MapleCharacter character, String inviteName) {
        if(StringUtil.isEmpty(inviteName))return;

        MapleMessenger messenger = character.getMessenger();
        if(messenger == null) return;
        if(messenger.isFull()) return;
        final MapleCharacter inviteCharacter = ChannelServerGroup.getCharacter(inviteName);
        if(inviteCharacter == null) {
            character.sendPacket(MessengerPacketCreator.messengerNote(inviteName, 4, 0));
        }else{
            if(inviteCharacter.getMessenger() != null){
                character.sendPacket(MessengerPacketCreator.messengerChat(character.getName() + " : " + inviteCharacter.getName() + " 忙碌中."));
            }else{
                character.sendPacket(MessengerPacketCreator.messengerNote(inviteName, 4, 0));
            }
        }
    }

    private void handleExitMessengerOperation(MapleCharacter character) {
        WorldOperation.Messenger.leaveMessenger(character);
    }

    private void handleOpenMessengerOperation(MapleCharacter character, LittleEndianAccessor slea) {
        if(ObjectUtil.isNotNull(character.getMessenger())) return;
        int messengerId = slea.readInt();
        if (messengerId == 0) {
            //创建聊天室
            WorldOperation.Messenger.createMessenger(character);
        } else {
            //加入
            WorldOperation.Messenger.joinMessenger(messengerId, character);
        }
    }


    /**
     * 联盟聊天
     *
     * @param character
     * @param content
     */
    private void handleAllianceChatOperation(MapleCharacter character, String content) {
        if(ObjectUtil.isNull(character.getGuildId())) return;

        MapleGuild guild = WorldOperation.Guild.getGuild(character.getGuildId());
        if(ObjectUtil.isNull(guild)) return;
        if(ObjectUtil.isNull(guild.getAllianceId())) return;

        WorldOperation.Alliance.chat(guild.getAllianceId(), character.getName() , content);
    }

    /**
     * 家族聊天
     *
     * @param character
     * @param content
     */
    private void handleGuildChatOperation(MapleCharacter character, String content) {
        if(ObjectUtil.isNull(character.getGuildId())) return;

        WorldOperation.Guild.chat(character.getGuildId(), character.getName() , content);
    }

    /**
     * 组队对话
     * @param character
     * @param content
     */
    private void handlePartyChatOperation(MapleCharacter character, String content) {
        if(ObjectUtil.isNull(character.getParty())) return;

        WorldOperation.Party.chat(character.getParty().getId(), character.getName(), content);
    }


    /**
     * 好友间对话
     * @param character
     * @param recipients
     * @param content
     */
    private void handleBuddyChatOperation(MapleCharacter character, int[] recipients, String content) {
        WorldOperation.Buddy.chat(recipients, character.getPlayerId(), character.getName() , content);
    }

}
