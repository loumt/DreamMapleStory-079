package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleParty;
import cn.ms.dm.server.client.MaplePartyCharacter;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author LouMT
 * @name PartyExecutor
 * @date 2026-03-03 16:01
 * @email lmtemail163@163.com
 * @description 组队
 */
@Component
@Slf4j(topic = "【组队】")
public class PartyExecutor {
    private static final AtomicInteger partyNumber = new AtomicInteger();
    private static final Map<Integer, MapleParty> parties = new HashMap<>();

    public MapleParty getParty(Integer partyId){
        return ObjectUtil.isNull(partyId)? null : parties.get(partyId);
    }

    /**
     * 新建组队
     */
    public MapleParty createParty(MaplePartyCharacter character){
        int partyId = partyNumber.incrementAndGet();
        MapleParty party = new MapleParty(partyId, character);
        parties.put(partyId, party);
        return party;
    }

    /**
     * 聊天
     */
    public void chat(Integer partyId, String senderName, String content) {
        MapleParty party = getParty(partyId);
        if(party == null) return;

        for (MaplePartyCharacter member : party.getMembers()) {
            if(member.getName().equals(senderName)) continue;

            MapleCharacter character = ChannelServerGroup.getCharacter(member.getId());
            if(character == null) continue;

            character.sendPacket(MessagePacketCreator.multiChat(senderName, content, ChatType.PARTY));
        }
    }
}
