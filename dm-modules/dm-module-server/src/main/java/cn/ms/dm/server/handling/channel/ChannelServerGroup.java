package cn.ms.dm.server.handling.channel;

import cn.ms.dm.server.client.MapleCharacter;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LouMT
 * @name ChannelServerGroup
 * @date 2026-02-11 14:58
 * @email lmtemail163@163.com
 * @description 频道管理群组
 */
@Slf4j
public class ChannelServerGroup {
    private final static Map<Integer, ChannelServer> instances = new HashMap<>();

    /**
     * 加入频道
     * @param channelNo 频道号
     * @param server 频道服务
     */
    public static void addChannelServer(Integer channelNo, ChannelServer server){
        instances.put(channelNo, server);
    }


    /**
     * 获取频道服务
     */
    public static ChannelServer getChannelServer(Integer channelNo){
        return instances.get(channelNo);
    }


    /**
     * 获取所有频道服务
     */
    public static Collection<ChannelServer> getChannelServers() {
        return Collections.unmodifiableCollection(instances.values());
    }

    /**
     * 获取各个频道连接数据
     */
    public static Map<Integer, Integer> getOnlineStatus(){
        Map<Integer, Integer> rs = Maps.newHashMap();
        for (ChannelServer cs : instances.values()) {
               rs.put(cs.getChannelNo(), cs.getCharacterSize());
        }
        return rs;
    }

    /**
     * 判断是否有频道
     */
    public static boolean noChannel(){
        return instances.isEmpty();
    }

    /**
     * 获取在线数量
     */
    public static Integer getOnlineClient(){
        Integer sum = 0;
        for (ChannelServer cs : instances.values()) {
            sum += cs.getCharacterSize();
        }
        return sum;
    }

    public static MapleCharacter getCharacter(final Integer characterId) {
        for (ChannelServer cs : instances.values()) {
            MapleCharacter c = cs.getStorage().getCharacterById(characterId);
            if(c!= null) return c;
        }
        return null;
    }

    public static MapleCharacter getCharacter(final Integer channelNo, final Integer characterId) {
        if(!instances.containsKey(channelNo)) return null;
        return instances.get(channelNo).getCharacter(characterId);
    }

    public static MapleCharacter getCharacter(final String characterName) {
        for (ChannelServer cs : instances.values()) {
            MapleCharacter c = cs.getStorage().getCharacterByName(characterName);
            if(c!= null) return c;
        }
        return null;
    }

    public static MapleCharacter getCharacter(final Integer channelNo, final String characterName) {
        if(!instances.containsKey(channelNo)) return null;
        return instances.get(channelNo).getCharacter(characterName);
    }

    /**
     * 发送数据包
     */
    public static void sendPacket(final Integer characterId, final byte[] packet){
        MapleCharacter character = getCharacter(characterId);
        if(character!=null) character.getClient().sendPacket(packet);
    }
}
