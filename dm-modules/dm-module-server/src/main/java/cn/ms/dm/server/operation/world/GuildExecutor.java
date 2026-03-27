package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.account.Characters;
import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.domain.guild.vo.GuildCharacterVO;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.client.MapleGuildCharacter;
import cn.ms.dm.server.database.service.CharacterService;
import cn.ms.dm.server.database.service.GuildService;
import cn.ms.dm.server.operation.WorldOperation;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
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
 * @name GuildExecuter
 * @date 2026-03-03 15:31
 * @email lmtemail163@163.com
 * @description 公会
 */
@Component
@Slf4j(topic = "【公会】")
@RequiredArgsConstructor
public class GuildExecutor {
    private final GuildService guildService;
    private static final Map<Integer, MapleGuild> guilds = new LinkedHashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void init(){
        log.info("【读取中】 Guilds :::");
        this.loadAll();
    }

    private void loadAll() {
        List<Guild> gs = guildService.list();
        for (Guild g : gs) {
            guilds.put(g.getId().intValue(), convert(g));
        }
    }

    public void refresh(Integer guildId){
        getGuild(guildId);
    }

    private MapleGuild convert(Guild g){
        MapleGuild gd = new MapleGuild();
        gd.setId(g.getId().intValue());
        gd.setName(g.getName());
        gd.setCapacity(g.getCapacity());
        gd.setAllianceId(g.getAllianceId().intValue());
        //成员
        List<GuildCharacterVO> members = guildService.getMembers(g.getId());
        for (GuildCharacterVO character : members) {
            MapleGuildCharacter mgc = new MapleGuildCharacter();
            mgc.setId(character.getCharacterId().intValue());
            mgc.setName(character.getName());
            mgc.setLevel(character.getLevel());
            mgc.setRank(character.getRank());
            mgc.setJob(character.getJob());
            gd.addMember(mgc);
        }
        return gd;
    }

    public MapleGuild getGuild(Integer guildId) {
        MapleGuild guild = null;
        lock.readLock().lock();
        try {
            guild = guilds.get(guildId);
            if(ObjectUtil.isNull(guild)){
                Guild gd = guildService.getById(guildId);
                if(ObjectUtil.isNotNull(gd)){
                    guild = convert(gd);
                    guilds.put(gd.getId().intValue(), guild);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return guild;
    }

    /**
     * 离开家族
     * 1.广播
     * 2.离开联盟
     */
    public void leave(final Integer guildId, final Integer characterId) {
        MapleGuild guild = getGuild(guildId);
        if(guild != null && guild.hasMember(characterId)){
            guild.leaveMember(characterId);

            Integer allianceId = guild.getAllianceId();
            if(allianceId != null) {
                WorldOperation.Alliance.leaveMember(allianceId, characterId);
            }
        }
    }

    public GuildRankType getRank(Integer guildId, Integer characterId) {
        MapleGuild guild = guilds.get(guildId);
        if(ObjectUtil.isNull(guild)) return GuildRankType.MEMBER;
        return guild.getRank(characterId);
    }

    public Integer getLeaderId(String name) {
        for (MapleGuild guild : guilds.values()) {
            if(guild.getName().equalsIgnoreCase(name)){
                return guild.getLeaderId();
            }
        }
        return null;
    }

    public Integer getLeaderId(Integer guildId){
        final MapleGuild guild = getGuild(guildId);
        if(guild == null) return null;
        return guild.getLeaderId();
    }


    public void setInvitedAllianceId(Integer inviteGuildId, Integer allianceId) {
        final MapleGuild inviteGuild = getGuild(inviteGuildId);
        if(inviteGuild != null) inviteGuild.setInvitedAllianceId(allianceId);
    }

    public Integer getInviteAllianceId(Integer guildId) {
        final MapleGuild guild = guilds.get(guildId);
        if(ObjectUtil.isNull(guild)) return null;
        return guild.getInvitedAllianceId();
    }

    public void clearInvitedAllianceId(Integer guildId) {
        final MapleGuild guild = guilds.get(guildId);
        if(ObjectUtil.isNotNull(guild)) guild.setInvitedAllianceId(null);
    }

    public List<MapleGuildCharacter> getMembers(Integer guildId) {
        final MapleGuild guild = guilds.get(guildId);
        if(guild == null) return Lists.newArrayList();
        return guild.getMembers();
    }

    public boolean isMember(Integer guildId, Integer playerId) {
        final MapleGuild guild = guilds.get(guildId);
        if(guild == null) return false;
        return guild.isMember(playerId);
    }

    public void expelMember(Integer guildId, Integer playerId) {
        final MapleGuild guild = guilds.get(guildId);
        if(guild == null) return;
        guild.expelMember(playerId);
    }
}
