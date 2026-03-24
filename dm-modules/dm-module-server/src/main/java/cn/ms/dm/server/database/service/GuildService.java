package cn.ms.dm.server.database.service;

import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.server.database.mapper.GuildMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
