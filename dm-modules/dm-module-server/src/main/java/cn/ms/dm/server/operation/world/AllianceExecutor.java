package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.alliance.vo.AllianceDetailVO;
import cn.ms.dm.domain.alliance.vo.AllianceGuildVO;
import cn.ms.dm.domain.alliance.vo.AllianceRankVO;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.server.client.MapleAlliance;
import cn.ms.dm.server.client.MapleAllianceGuild;
import cn.ms.dm.server.client.MapleAllianceRank;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.database.service.AllianceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        //公会集合
        for (AllianceGuildVO guild : detail.getGuilds()) {
            MapleAllianceGuild mag = new MapleAllianceGuild();
            mag.setName(guild.getName());
            mag.setId(guild.getGuildId().intValue());
            ga.add(mag);
        }

        //职级集合
        for (AllianceRankVO rank : detail.getRanks()) {
            MapleAllianceRank mar = new MapleAllianceRank();
            mar.setCharacterId(rank.getCharacterId().intValue());
            mar.setName(rank.getName());
            mar.setRank(rank.getRank());
            if(rank.getRank() == AllianceRankType.LEADER) ga.setLeaderId(mar.getCharacterId());
            ga.add(mar);
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

    /**
     * 是否为对应职级
     */
    public boolean isRank(Integer allianceId, Integer characterId, AllianceRankType type) {
        MapleAlliance alliance = alliances.get(allianceId);
        if(alliance == null) return false;
        return alliance.isRank(characterId, type);
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
            alliance.removeRank(characterId);
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

        alliance.removeGuild(guildId);
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

        alliance.setRank(characterId, changeRank);
    }

    public void updateNotice(Integer allianceId, String notice) {
        final MapleAlliance alliance = getAlliance(allianceId);
        if(alliance == null) return;
        alliance.updateNotice(notice);
    }
}
