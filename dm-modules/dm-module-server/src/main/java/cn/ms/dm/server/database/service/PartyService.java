package cn.ms.dm.server.database.service;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.guild.GuildRank;
import cn.ms.dm.domain.party.Party;
import cn.ms.dm.domain.party.PartyRank;
import cn.ms.dm.server.database.mapper.PartyMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name PartyService
 * @date 2026-03-24 15:39
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PartyService extends ServiceImpl<PartyMapper, Party> {
    private final PartyRankService partyRankService;


    public Party findCharacterParty(Long characterId) {
        PartyRank rank = partyRankService.getOne(Wrappers.lambdaQuery(PartyRank.class).eq(PartyRank::getCharacterId, characterId));
        if(ObjectUtil.isNull(rank)) return null;
        return getById(rank.getPartyId());
    }
}
