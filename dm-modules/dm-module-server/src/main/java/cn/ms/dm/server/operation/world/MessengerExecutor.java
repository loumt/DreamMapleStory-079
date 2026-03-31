package cn.ms.dm.server.operation.world;

import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleMessenger;
import cn.ms.dm.server.client.MapleMessengerCharacter;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.MessengerPacketCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LouMT
 * @name MessengerExecutor
 * @date 2026-03-03 16:25
 * @email lmtemail163@163.com
 * @description 聊天室
 */
@Slf4j(topic = "【聊天室】")
@Component
public class MessengerExecutor {
    private static final Map<Integer, MapleMessenger> messengers = new HashMap<>();
    private static final AtomicInteger runningMessengerId = new AtomicInteger();

    static {
        runningMessengerId.set(1);
    }

    /**
     * 创建聊天室
     */
    public void createMessenger(MapleCharacter character) {
        int messengerId = runningMessengerId.getAndIncrement();
        MapleMessenger mm = new MapleMessenger(messengerId, new MapleMessengerCharacter(character));
        messengers.put(messengerId, mm);
        character.setMessenger(mm);
    }

    public MapleMessenger getMessenger(Integer messengerId) {
        return messengers.get(messengerId);
    }

    /**
     * 加入聊天室
     */
    public void joinMessenger(Integer messengerId, MapleCharacter mine) {
        MapleMessengerCharacter mmc = new MapleMessengerCharacter(mine);
        MapleMessenger messenger = getMessenger(messengerId);
        if(messenger == null) return;
        if(messenger.isFull()) return;
        mine.setMessenger(messenger);
        messenger.join(mmc);

        int position = messenger.getPositionByName(mmc.getName());
        for (MapleMessengerCharacter member : messenger.getMembers()) {
            if(member == null) continue;
            MapleCharacter character = ChannelServerGroup.getCharacter(member.getName());
            if(character == null) return;
            int pos = messenger.getPositionByName(member.getName());
            if(!member.getName().equals(mmc.getName())){
                character.sendPacket(MessengerPacketCreator.addMessengerPlayer(mmc.getName(), mine, position, mmc.getChannelNo()));
                mine.sendPacket(MessengerPacketCreator.addMessengerPlayer(member.getName(), character, pos, member.getChannelNo()));
            }else{
                character.sendPacket(MessengerPacketCreator.joinMessenger(pos));
            }
        }
    }

    /**
     * 离开聊天室
     */
    public void leaveMessenger(MapleCharacter character){
        if(character.getMessenger() == null) return;
        final MapleMessengerCharacter mmc = new MapleMessengerCharacter(character);
        MapleMessenger messenger = character.getMessenger();
        int position = messenger.getPositionByName(mmc.getName());
        messenger.leave(mmc);
        //向其他玩家发送离开数据包
        for (MapleMessengerCharacter member : messenger.getMembers()) {
            MapleCharacter otherCharacter = ChannelServerGroup.getCharacter(member.getName());
            if(otherCharacter == null) return;
            otherCharacter.sendPacket(MessengerPacketCreator.removeMessengerPlayer(position));
        }
    }

    /**
     * 发送消息
     */
    public void messengerChat(Integer messengerId, String senderName, String content) {
        MapleMessenger messenger = getMessenger(messengerId);
        if(messenger == null) return;

        for (MapleMessengerCharacter mmc : messenger.getMembers()) {
            if(mmc == null || mmc.getName().equals(senderName)) return;

            MapleCharacter character = ChannelServerGroup.getCharacter(mmc.getName());
            if(character == null) return;
            character.sendPacket(MessengerPacketCreator.messengerChat(content));
        }
    }
}
