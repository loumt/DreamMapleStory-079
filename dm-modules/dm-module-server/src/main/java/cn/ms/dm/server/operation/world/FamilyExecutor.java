package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.domain.account.Characters;
import cn.ms.dm.domain.family.Family;
import cn.ms.dm.server.client.MapleFamily;
import cn.ms.dm.server.client.MapleFamilyCharacter;
import cn.ms.dm.server.database.service.CharacterService;
import cn.ms.dm.server.database.service.FamilyService;
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
 * @name FamilyExecutor
 * @date 2026-03-04 14:40
 * @email lmtemail163@163.com
 * @description 家族
 */
@Component
@Slf4j(topic = "【家族】")
@RequiredArgsConstructor
public class FamilyExecutor {
    private final FamilyService familyService;
    private final CharacterService characterService;
    private static final Map<Long, MapleFamily> families = new LinkedHashMap<>();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @PostConstruct
    public void init(){
        log.info("【读取中】 Family :::");
        this.loadAll();
    }

    private void loadAll() {
        List<Family> fs = familyService.list();
        for (Family f : fs) {
            families.put(f.getId(), convertMapleFamily(f));
        }
    }

    private MapleFamily convertMapleFamily(Family fy){
        MapleFamily mf = new MapleFamily();
        mf.setId(fy.getId().intValue());
        mf.setName(fy.getName());
        //成员
        LambdaQueryWrapper<Characters> wq = Wrappers.lambdaQuery(Characters.class).eq(Characters::getFamilyId, fy.getId());
        List<Characters> characters = characterService.list(wq);
        for (Characters character : characters) {
            MapleFamilyCharacter mfc = new MapleFamilyCharacter();
            mfc.setId(character.getId().intValue());
            mfc.setName(character.getName());
            mfc.setLevel(character.getLevel());
            mf.addMember(character.getId(), mfc);
        }
        return mf;
    }

    public MapleFamily getFamily(Long familyId){
        MapleFamily family = null;
        lock.readLock().lock();
        try {
            family = families.get(familyId);
            if(ObjectUtil.isNull(family)){
                Family fy = familyService.getById(familyId);
                if(ObjectUtil.isNotNull(fy)){
                    family = convertMapleFamily(fy);
                    families.put(fy.getId(), family);
                }
            }
        } finally {
            lock.readLock().unlock();
        }
        return family;
    }

}
