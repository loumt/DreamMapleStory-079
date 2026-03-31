package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.utils.StreamUtils;
import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.alliance.AllianceGuildCharacter;
import cn.ms.dm.domain.alliance.vo.AllianceDetailVO;
import cn.ms.dm.domain.alliance.vo.AllianceGuildCharacterVO;
import cn.ms.dm.domain.alliance.vo.AllianceGuildVO;
import cn.ms.dm.domain.alliance.vo.AllianceRankVO;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.maple.constant.packet.ChatType;
import cn.ms.dm.server.client.MapleAlliance;
import cn.ms.dm.server.client.MapleAllianceCharacter;
import cn.ms.dm.server.client.MapleAllianceGuild;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.database.service.AllianceGuildCharacterService;
import cn.ms.dm.server.database.service.AllianceService;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LouMT
 * @name AllianceExecutor
 * @date 2026-03-03 15:47
 * @email lmtemail163@163.com
 * @description 联盟操作
 */
@Component
@Slf4j(topic = "【联盟】")
@RequiredArgsConstructor
public class AllianceExecutor {
    private final AllianceService allianceService;
    private static final Map<Integer, MapleAlliance> alliances = new LinkedHashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void init(){
        log.info("【读取中】 Alliance :::");
        this.loadAll();
    }

    private void loadAll() {
        List<Alliance> as = allianceService.list();
        for (Alliance a : as) {
            alliances.put(a.getId().intValue(), convertMapleAlliances(a));
        }
    }


    private MapleAlliance convertMapleAlliances(Alliance a){
        MapleAlliance ga = new MapleAlliance();
        ga.setId(a.getId().intValue());
        ga.setName(a.getName());
        ga.setNotice(a.getNotice());
        ga.setCapacity(a.getCapacity());
        AllianceDetailVO detail = allianceService.detail(a.getId());
        //职级集合
        for (AllianceRankType value : AllianceRankType.values()) {
            Optional<AllianceRankVO> first = StreamUtils.findFirst(detail.getRanks(), vo -> vo.getRank() == value);
            ga.setRank(value, first.isEmpty()? value.getTitle(): first.get().getTitle());
        }
        //公会集合
        for (AllianceGuildVO guild : detail.getGuilds()) {
            MapleAllianceGuild mag = new MapleAllianceGuild();
            mag.setId(guild.getGuildId().intValue());
            mag.setName(guild.getName());
            ga.add(mag);
        }
        //成员
        List<AllianceGuildCharacterVO> members = allianceService.getMembers(a.getId());
        for (AllianceGuildCharacterVO member : members) {
            MapleAllianceCharacter agc = new MapleAllianceCharacter();
            agc.setCharacterId(member.getCharacterId().intValue());
            agc.setName(member.getName());
            agc.setRank(agc.getRank());
            if(agc.getRank() == AllianceRankType.LEADER) ga.setLeaderId(member.getCharacterId().intValue());
            ga.getMembers().put(member.getCharacterId().intValue(), agc);
        }
        return ga;
    }


    public MapleAlliance getAlliance(Integer allianceId){
        return alliances.get(allianceId);
    }

    /**
     * 获取联盟长ID
     */
    public Integer getLeaderId(Integer allianceId){
        MapleAlliance alliance = alliances.get(allianceId);
        if(ObjectUtil.isNull(alliance)) return null;
        return alliance.getLeaderId();
    }

    public AllianceRankType getRank(Integer allianceId, Integer characterId){
        MapleAlliance alliance = alliances.get(allianceId);
        if(alliance == null) return null;
        return alliance.getRank(characterId);
    }


    public boolean canInvite(Integer allianceId) {
        MapleAlliance alliance = alliances.get(allianceId);
        return alliance.getCapacity() > alliance.getGuilds().size();
    }

    /**
     * 玩家离开联盟
     * 职级调动
     * 一般由离开家族触发
     */
    public void leaveMember(Integer allianceId, Integer characterId) {
        MapleAlliance alliance = alliances.get(allianceId);
        if(alliance!= null) {
            alliance.leaveMember(characterId);
        }
    }

    /**
     * 家族加入联盟
     */
    public void joinGuild(Integer allianceId, MapleGuild guild) {
        final MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        if(alliance.getCapacity() >= alliance.getGuilds().size()) return;

        MapleAllianceGuild mag = new MapleAllianceGuild();
        mag.setId(guild.getId());
        mag.setName(guild.getName());
        alliance.add(mag);
    }

    public void removeGuild(Integer allianceId, Integer guildId) {
        MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;

        alliance.leaveGuild(guildId);
    }

    public void changeLeader(Integer allianceId, Integer characterId) {
        MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        alliance.changeLeader(characterId);
    }

    public void changeRank(Integer allianceId, Integer characterId, int changeRankCode) {
        MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        if(ObjectUtil.equal(alliance.getLeaderId(), characterId)) return;
        AllianceRankType changeRank = AllianceRankType.codeOf(changeRankCode);
        if(changeRank == null) return;
        if(changeRank == AllianceRankType.LEADER) return;

        AllianceRankType nowRank = alliance.getRank(characterId);
        if(changeRank == nowRank) return;

        alliance.changeRank(characterId, changeRank);
    }

    public void updateNotice(Integer allianceId, String notice) {
        final MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        alliance.updateNotice(notice);
    }

    public void chat(Integer allianceId, String senderName, String content) {
        final MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        alliance.sendPacket(MessagePacketCreator.multiChat(senderName, content, ChatType.ALLIANCE));
    }
}
