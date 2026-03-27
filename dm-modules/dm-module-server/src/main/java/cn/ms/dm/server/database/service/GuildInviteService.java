package cn.ms.dm.server.database.service;

import cn.ms.dm.core.enums.YesOrNo;
import cn.ms.dm.domain.guild.GuildInvite;
import cn.ms.dm.domain.guild.GuildRank;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.server.database.mapper.GuildInviteMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author LouMT
 * @name GuildInviteService
 * @date 2026-03-27 15:55
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class GuildInviteService extends ServiceImpl<GuildInviteMapper, GuildInvite> {

    public void createInvite(Integer playerId, Integer guildId, Integer acceptorId) {
        LambdaQueryWrapper<GuildInvite> wq = Wrappers.lambdaQuery(GuildInvite.class)
                .eq(GuildInvite::getGuildId, guildId)
                .eq(GuildInvite::getAcceptorId, playerId)
                .isNull(GuildInvite::getAccept);
        GuildInvite invite = getOne(wq);
        if(invite == null){
            GuildInvite inviteRecord = new GuildInvite();
            inviteRecord.setGuildId(guildId.longValue());
            inviteRecord.setAcceptorId(acceptorId.longValue());
            inviteRecord.setInvitorId(playerId.longValue());
            inviteRecord.setExpireTime(LocalDateTime.now().plusMinutes(60));
            save(inviteRecord);
        }else{
            update(Wrappers.lambdaUpdate(GuildInvite.class).eq(GuildInvite::getId, invite.getId()).set(GuildInvite::getExpireTime, LocalDateTime.now().plusMinutes(60)));
        }
    }

    public boolean acceptInvite(Integer guildId, Integer playerId) {
        LambdaQueryWrapper<GuildInvite> wq = Wrappers.lambdaQuery(GuildInvite.class)
                .eq(GuildInvite::getGuildId, guildId)
                .eq(GuildInvite::getAcceptorId, playerId)
                .isNull(GuildInvite::getAccept)
                .lt(GuildInvite::getExpireTime, LocalDateTime.now());
        GuildInvite invite = getOne(wq);
        if(invite == null) return false;

        LambdaUpdateWrapper<GuildInvite> uq = Wrappers.lambdaUpdate(GuildInvite.class)
                .eq(GuildInvite::getId, invite.getId())
                .set(GuildInvite::getAcceptTime, LocalDateTime.now())
                .set(GuildInvite::getAccept, YesOrNo.YES);
        update(uq);
        return true;
    }
}
