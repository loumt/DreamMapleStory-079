package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.alliance.AllianceGuildCharacter;
import cn.ms.dm.domain.alliance.vo.AllianceDetailVO;
import cn.ms.dm.domain.alliance.vo.AllianceGuildCharacterVO;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.server.database.mapper.AllianceMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LouMT
 * @name AlliancesService
 * @date 2026-03-24 16:40
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AllianceService extends ServiceImpl<AllianceMapper, Alliance> {
    private final AllianceGuildCharacterService allianceGuildCharacterService;



    public AllianceDetailVO detail(Long id) {
        return this.getBaseMapper().detail(id);
    }

    public AllianceGuildCharacter getLeader(Long id){
        LambdaQueryWrapper<AllianceGuildCharacter> wq = Wrappers.lambdaQuery(AllianceGuildCharacter.class)
                .eq(AllianceGuildCharacter::getAllianceId, id)
                .eq(AllianceGuildCharacter::getRank, AllianceRankType.LEADER)
                .last("limit 1");
        return allianceGuildCharacterService.getOne(wq);
    }

    public List<AllianceGuildCharacterVO> getMembers(Long id){
        return allianceGuildCharacterService.details(id);
    }
}
