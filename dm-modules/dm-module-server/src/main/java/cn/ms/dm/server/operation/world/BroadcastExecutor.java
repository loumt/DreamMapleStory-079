package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleMap;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.List;

/**
 * @author LouMT
 * @name BroadcastExecutor
 * @date 2026-03-03 16:27
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【广播】")
@Component
public class BroadcastExecutor {

    public void send(final Integer characterId, final byte[] packet){
        MapleCharacter character = ChannelServerGroup.getCharacter(characterId);
        if(character != null) character.getClient().sendPacket(packet);
    }

    public void send(final MapleCharacter sourceCharacter, final String content) {
        MapleMap map = sourceCharacter.getMap();
        MapleClient client = sourceCharacter.getClient();
        String message = "<"+ client.getAccountVip() +"> " + sourceCharacter.getName() + ": " + content;

        _send(MessagePacketCreator.yellowChat(message),
                map,
                sourceCharacter,
                null,
                Double.POSITIVE_INFINITY
        );

        _send(MessagePacketCreator.getChatText(sourceCharacter.getPlayerId().longValue(), content, false, 1),
                map,
                sourceCharacter,
                null,
                Double.POSITIVE_INFINITY);
    }

    private static void _send(final byte[] packet, final MapleMap map, final MapleCharacter sourceCharacter, final Point sourcePoint, final Double rangeLimit) {
        final List<MapleCharacter> characters = map.getCharacters();
        for (MapleCharacter character : characters) {
            if (ObjectUtil.equal(character, sourceCharacter)) continue;

            if(rangeLimit.isInfinite()){
                character.getClient().sendPacket(packet);
            }else{
                //判断距离
                if(sourcePoint.distanceSq(character.getPosition()) <= rangeLimit){
                    character.getClient().sendPacket(packet);
                }
            }
        }
    }
}
