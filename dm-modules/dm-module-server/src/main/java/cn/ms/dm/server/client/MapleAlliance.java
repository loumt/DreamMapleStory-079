package cn.ms.dm.server.client;

import cn.ms.dm.core.utils.StreamUtils;
import cn.ms.dm.domain.alliance.AllianceGuildCharacter;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.AlliancePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author LouMT
 * @name MapleGuildAlliance
 * @date 2026-03-03 15:49
 * @email lmtemail163@163.com
 * @description 联盟信息
 */
@Getter
@Setter
public class MapleAlliance implements Serializable {
    @Serial
    private static final long serialVersionUID = 38658444427542113L;
    //联盟信息
    private Integer id,leaderId, capacity;
    private String name, notice;
    //公会
    private Map<Integer, MapleAllianceGuild> guilds = Maps.newConcurrentMap();
    //成员职级
    private String[] ranks = new String[5];
    //成员
    private Map<Integer, MapleAllianceCharacter> members = Maps.newConcurrentMap();


    public void add(final MapleAllianceGuild mag) {
        if(guilds.containsKey(mag.getId()) || guilds.size() >= capacity) return;
        guilds.put(mag.getId(), mag);
    }

    public void updateNotice(String notice) {
        setNotice(notice);
        //更新通知
        sendPacket(AlliancePacketCreator.getAllianceUpdate(this));
        //通告
        sendPacket(MessagePacketCreator.serverNotice(5,  "联盟公告事项: " + notice));
    }

    /**
     * 向所有子家族发送数据包
     */
    public void sendPacket(final byte[] packet) {
        for (Integer guildId : guilds.keySet()) {
            MapleGuild mapleGuild = WorldOperation.Guild.getGuild(guildId);
            mapleGuild.sendPacket(packet);
        }
    }

    public String getRank(AllianceRankType idx){
        return ranks[idx.getCode()];
    }

    public void setRank(AllianceRankType rank, String title) {
        ranks[rank.getCode()] = title;
    }

    public AllianceRankType getRank(Integer characterId) {
        MapleAllianceCharacter character = members.get(characterId);
        return character == null ? null: character.getRank();
    }

    /**
     * 成员离开-》仅由家族触发
     */
    public void leaveMember(Integer characterId) {
        members.remove(characterId);
    }

    /**
     * 家族离开
     */
    public void leaveGuild(Integer guildId) {
        guilds.remove(guildId);
    }


    /**
     * 改变联盟长
     */
    public void changeLeader(Integer characterId) {
        Integer originLeaderId = this.getLeaderId();
        this.setLeaderId(characterId);

        MapleAllianceCharacter character = members.get(characterId);
        character.setRank(AllianceRankType.LEADER);
        members.put(characterId, character);

        MapleAllianceCharacter originLeader = members.get(originLeaderId);
        originLeader.setRank(AllianceRankType.MEMBER);
        members.put(originLeaderId, originLeader);
    }

    /**
     * 改变玩家职级
     */
    public void changeRank(Integer characterId, AllianceRankType rank) {
        MapleAllianceCharacter character = members.get(characterId);
        character.setRank(rank);
        members.put(characterId, character);
    }
}
