package cn.ms.dm.server.database.service;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.domain.guild.GuildRank;
import cn.ms.dm.domain.guild.vo.GuildCharacterVO;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.server.database.mapper.GuildMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author LouMT
 * @name GuildService
 * @date 2026-03-04 13:39
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class GuildService extends ServiceImpl<GuildMapper, Guild> {

    @Resource
    private GuildRankService guildRankService;

    /**
     * 检索成员
     */
    public List<GuildCharacterVO> getMembers(Long guildId) {
        return this.getBaseMapper().selectMembers(guildId);
    }

    public Guild findCharacterGuild(Long characterId) {
        GuildRank rank = guildRankService.getOne(Wrappers.lambdaQuery(GuildRank.class).eq(GuildRank::getCharacterId, characterId));
        if(ObjectUtil.isNull(rank)) return null;
        return getById(rank.getGuildId());
    }

    public void deleteCharacters(Long characterId) {
        guildRankService.remove(Wrappers.lambdaQuery(GuildRank.class).eq(GuildRank::getCharacterId, characterId));
    }

    public Long createGuild(String name, Long characterId) {
        Guild guild = new Guild();
        guild.setName(name);
        save(guild);

        GuildRank rank = new GuildRank();
        rank.setCharacterId(characterId);
        rank.setGuildId(guild.getId());
        rank.setJoinTime(LocalDateTime.now());
        rank.setRank(GuildRankType.LEADER);
        guildRankService.save(rank);
        return guild.getId();
    }
}
