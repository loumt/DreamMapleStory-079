package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.alliance.Alliance;
import cn.ms.dm.domain.alliance.vo.AllianceDetailVO;
import cn.ms.dm.server.database.mapper.AllianceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author LouMT
 * @name AlliancesService
 * @date 2026-03-24 16:40
 * @email lmtemail163@163.com
 * @description
 */
@Service
@Slf4j
public class AllianceService extends ServiceImpl<AllianceMapper, Alliance> {

    public AllianceDetailVO detail(Long id) {
        return this.getBaseMapper().detail(id);
    }
}
