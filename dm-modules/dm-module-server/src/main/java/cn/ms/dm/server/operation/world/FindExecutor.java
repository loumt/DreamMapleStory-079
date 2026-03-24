package cn.ms.dm.server.operation.world;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LouMT
 * @name FindExecutor
 * @date 2026-03-06 14:26
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【查询器】")
@Component
public class FindExecutor {
    private static final Map<Integer, Integer> CHARACTER_CHANNEL_MAP = new ConcurrentHashMap<>();

    public void register(Integer characterId, int channelNo) {
        CHARACTER_CHANNEL_MAP.put(characterId, channelNo);
    }

    public void deregister(Integer characterId) {
        CHARACTER_CHANNEL_MAP.remove(characterId);
    }

    public Integer findChannelNoByCharacterId(Integer characterId){
        Integer channelNo = CHARACTER_CHANNEL_MAP.get(characterId);
        if(ObjectUtil.isNull(ChannelServerGroup.getChannelServer(channelNo))){
            deregister(characterId);
            return null;
        }
        return channelNo;
    }
}
