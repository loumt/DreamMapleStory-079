package cn.ms.dm.server.operation.world;

import cn.ms.dm.core.utils.StreamUtils;
import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.buddy.Buddy;
import cn.ms.dm.domain.buddy.BuddySlot;
import cn.ms.dm.domain.buddy.vo.BuddyVO;
import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.server.client.MapleAlliance;
import cn.ms.dm.server.client.MapleBuddy;
import cn.ms.dm.server.client.MapleBuddyStorage;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.database.service.BuddyService;
import cn.ms.dm.server.database.service.BuddySlotService;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * @author LouMT
 * @name BuddyExecutor
 * @date 2026-03-03 16:17
 * @email lmtemail163@163.com
 * @description 好友
 */
@Slf4j(topic = "【好友】")
@Component
@RequiredArgsConstructor
public class BuddyExecutor {
    private static final String DEFAULT_GROUP = "其他";
    private final BuddyService buddyService;
    private final BuddySlotService buddySlotService;
    private static final Map<Integer, MapleBuddyStorage> buddyStorage = new LinkedHashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void init(){
        log.info("【读取中】 Guilds :::");
        this.loadAll();
    }

    private void loadAll() {
        Map<Integer, Integer> slotMap = StreamUtils.toMap(buddySlotService.list(), bl -> bl.getCharacterId().intValue(), BuddySlot::getSlot);

        List<BuddyVO> bds = buddyService.listDetail();
        for (BuddyVO vo: bds) {
            Integer slot = slotMap.getOrDefault(vo.getCharacterId().intValue(), AppConfigProperties.BUDDY_SLOT);
            MapleBuddyStorage storage = buddyStorage.putIfAbsent(vo.getCharacterId().intValue(), new MapleBuddyStorage(slot));
            if(storage != null) {
                storage.put(new MapleBuddy(DEFAULT_GROUP, vo.getBuddyId().intValue(), vo.getName(), vo.getLevel(), vo.getJob(), -1, true));
            }
        }
    }

    public void chat(int[] recipientCharacterIds, Integer senderId, String senderName, String content){
        for (int recipientCharacterId : recipientCharacterIds) {
            MapleCharacter character = ChannelServerGroup.getCharacter(recipientCharacterId);
            if(character == null) continue;

            boolean flag = isBuddyVisible(character.getPlayerId(), senderId);
            if(flag){
                character.sendPacket(MessagePacketCreator.multiChat(senderName, content, ChatType.BUDDY));
            }
        }
    }

    /**
     * 是否为好友且可见
     */
    private boolean isBuddyVisible(Integer playerId, Integer buddyId) {
        MapleBuddyStorage storage = buddyStorage.get(playerId);
        if(storage == null) return false;
        return storage.containsVisible(buddyId);
    }

}
