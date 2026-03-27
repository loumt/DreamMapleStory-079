package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.buddy.Buddy;
import cn.ms.dm.server.database.mapper.BuddyMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name BuddyService
 * @date 2026-03-24 13:24
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class BuddyService extends ServiceImpl<BuddyMapper, Buddy> {

    public void deleteCharacters(Long characterId) {
        LambdaQueryWrapper<Buddy> dq = Wrappers.lambdaQuery(Buddy.class).eq(Buddy::getCharacterId, characterId).or().eq(Buddy::getBuddyId, characterId);
        remove(dq);
    }
}
