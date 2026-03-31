package cn.ms.dm.server.client;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author LouMT
 * @name MapleMessenger
 * @date 2026-03-03 16:21
 * @email lmtemail163@163.com
 * @description 聊天室
 */
@Getter
public class MapleMessenger {
    //房间编号
    private final int id;
    //房间玩家
    private final MapleMessengerCharacter[] members = new MapleMessengerCharacter[3];

    public MapleMessenger(int id, MapleMessengerCharacter mmc) {
        this.id = id;
        join(0, mmc);
    }

    public void join(int pos, MapleMessengerCharacter mmc){
        if(members[pos] != null) return;
        members[pos] = mmc;
    }

    public void join(MapleMessengerCharacter mmc){
        for (int i = 0;i< members.length; i++){
            if(members[i] != null) continue;
            members[i] = mmc;
        }
    }

    public void leave(MapleMessengerCharacter mmc){
        int pos = getPos(mmc);
        if(pos >= 0){
            members[pos] = null;
        }
    }

    public boolean isFull(){
        return getEmptyPos() < 0;
    }

    public int getEmptyPos(){
        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public int getPos(MapleMessengerCharacter mmc){
        return getPos(mmc.getName());
    }

    public int getPos(String name){
        for (int i = 0; i < members.length; i++) {
            MapleMessengerCharacter mmc = members[i];
            if (mmc != null && mmc.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public int getPositionByName(String name) {
        for (int i = 0; i < members.length; i++) {
            MapleMessengerCharacter messengerchar = members[i];
            if (messengerchar != null && messengerchar.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return 4;
    }

    public Collection<MapleMessengerCharacter> members(){ return Arrays.asList(members); }
}
