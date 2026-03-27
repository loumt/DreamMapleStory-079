package cn.ms.dm.server.client;

import cn.hutool.core.util.ObjectUtil;
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
    private final Map<Integer, MapleAllianceGuild> guilds = Maps.newHashMap();
    //成员职级
    private final Map<Integer,MapleAllianceRank> ranks  = Maps.newHashMap();;


    public void add(MapleAllianceGuild mag) {
        guilds.put(mag.getId(), mag);
    }

    public void add(MapleAllianceRank rank) {
        ranks.put(rank.getCharacterId(), rank);
    }

    public void removeRank(Integer characterId) {
        ranks.remove(characterId);
    }

    public boolean isRank(Integer characterId, AllianceRankType rank) {
        MapleAllianceRank mapleAllianceRank = ranks.get(characterId);
        if(mapleAllianceRank == null) return false;
        return mapleAllianceRank.getRank() == rank;
    }

    public AllianceRankType getRank(Integer characterId){
        MapleAllianceRank mapleAllianceRank = ranks.get(characterId);
        if(mapleAllianceRank == null) return null;
        return mapleAllianceRank.getRank();
    }

    public void setRank(Integer characterId, AllianceRankType rank) {
        MapleAllianceRank mapleAllianceRank = ranks.get(characterId);
        mapleAllianceRank.setRank(rank);
        ranks.put(characterId, mapleAllianceRank);
    }

    public void removeGuild(Integer guildId) {
        guilds.remove(guildId);
    }

    public void changeLeader(Integer characterId) {
        Integer oldLeaderId = getLeaderId();
        if(ObjectUtil.equal(characterId, oldLeaderId)) return;

        MapleAllianceRank downRank = ranks.get(oldLeaderId);
        downRank.setRank(AllianceRankType.MEMBER);
        ranks.put(oldLeaderId, downRank);

        MapleAllianceRank upRank = ranks.get(characterId);
        upRank.setRank(AllianceRankType.LEADER);
        ranks.put(characterId, upRank);

        setLeaderId(characterId);
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
        for (MapleAllianceGuild guild : guilds.values()) {
            MapleGuild mapleGuild = WorldOperation.Guild.getGuild(guild.getId());
            mapleGuild.sendPacket(packet);
        }
    }
}
