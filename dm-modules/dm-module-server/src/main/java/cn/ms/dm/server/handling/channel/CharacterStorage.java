package cn.ms.dm.server.handling.channel;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.operation.WorldOperation;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author LouMT
 * @name PlayerStorage
 * @date 2026-03-06 14:44
 * @email lmtemail163@163.com
 * @description 频道角色缓存
 * 需要绑定到频道服务器 ChannelServer
 */
@Getter
@Setter
public class CharacterStorage {
    //当前频道号
    private int channelNo;
    //角色缓存
    private final Map<Integer, MapleCharacter> CHARACTER_CACHE = Maps.newConcurrentMap();

    public CharacterStorage(int channelNo) {
        this.channelNo = channelNo;
    }

    public final void register(final MapleCharacter character){
        if(ObjectUtil.isNull(character)) return;
        //缓存
        CHARACTER_CACHE.put(character.getCharacterId(), character);
    }

    public final void deregister(final MapleCharacter character) {
        //删除缓存
        CHARACTER_CACHE.remove(character.getCharacterId());
    }

    public final Integer getCharacterSize(){
        return CHARACTER_CACHE.size();
    }

    public MapleCharacter getCharacterById(int characterId) {
        return CHARACTER_CACHE.get(characterId);
    }
}
