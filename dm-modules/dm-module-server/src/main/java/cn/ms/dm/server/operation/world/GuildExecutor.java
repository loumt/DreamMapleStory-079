package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.account.Characters;
import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.client.MapleGuildCharacter;
import cn.ms.dm.server.database.service.CharacterService;
import cn.ms.dm.server.database.service.GuildService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
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
    private final CharacterService characterService;
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
            guilds.put(g.getId().intValue(), convertMapleGuild(g));
        }
    }

    private MapleGuild convertMapleGuild(Guild g){
        MapleGuild gd = new MapleGuild();
        gd.setId(g.getId().intValue());
        gd.setName(g.getName());
        gd.setAllianceId(g.getAllianceId().intValue());
        //成员
        LambdaQueryWrapper<Characters> wq = Wrappers.lambdaQuery(Characters.class).eq(Characters::getGuildId, g.getId());
        List<Characters> characters = characterService.list(wq);
        for (Characters character : characters) {
            MapleGuildCharacter mgc = new MapleGuildCharacter();
            mgc.setId(character.getId().intValue());
            mgc.setName(character.getName());
            mgc.setLevel(character.getLevel());
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
                    guild = convertMapleGuild(gd);
                    guilds.put(gd.getId().intValue(), guild);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return guild;
    }

    public Long getGuildLeaderId(Long guildId){
        return null;
    }

    public Long getInviteId(Long guildId){
        return null;
    }

    public void setInviteId(final Long guildId, final Long inviteId){}


    public void leave(int characterId) {

    }
}
