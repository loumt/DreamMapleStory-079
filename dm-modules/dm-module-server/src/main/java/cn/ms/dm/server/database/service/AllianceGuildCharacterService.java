package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.alliance.AllianceGuildCharacter;
import cn.ms.dm.domain.alliance.vo.AllianceGuildCharacterVO;
import cn.ms.dm.server.database.mapper.AllianceGuildCharacterMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LouMT
 * @name AllianceGuildCharacterService
 * @date 2026-03-31 14:27
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class AllianceGuildCharacterService extends ServiceImpl<AllianceGuildCharacterMapper, AllianceGuildCharacter> {
    public List<AllianceGuildCharacterVO> details(Long id) {
        return getBaseMapper().details(id);
    }
}
