package cn.ms.dm.server.client;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.utils.StreamUtils;
import cn.ms.dm.domain.guild.GuildRankTitle;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author LouMT
 * @name MapleGuild
 * @date 2026-03-03 15:22
 * @email lmtemail163@163.com
 * @description 公会
 */
@Setter
@Getter
public class MapleGuild implements Serializable {
    @Serial
    private static final long serialVersionUID = 3609202007294275787L;
    //公会信息
    private Integer id, capacity;
    private String name;
    private String notice;
    //联盟信息
    private Integer allianceId;
    //邀请联盟ID
    private Integer invitedAllianceId;
    //职级
    private String[] ranks = new String[5];
    //公会成员
    private final List<MapleGuildCharacter> members = Lists.newCopyOnWriteArrayList();

    public void addMember(MapleGuildCharacter member){
        this.members.add(member);
    }

    public boolean hasMember(final Integer characterId){
        return members.stream().anyMatch(m -> ObjectUtil.equal(m.getId(), characterId));
    }

    /**
     * 退出家族
     */
    public void leaveMember(Integer characterId) {
        members.removeIf(member -> ObjectUtil.equal(member.getId(), characterId));
    }

    public GuildRankType getRank(Integer characterId){
        for (MapleGuildCharacter member : members) {
            if(ObjectUtil.equal(member.getId(), characterId)){
                return member.getRank();
            }
        }
        return GuildRankType.MEMBER;
    }

    public Integer getLeaderId() {
        Optional<MapleGuildCharacter> leaderOptional = members.stream().filter(member -> member.getRank() == GuildRankType.LEADER).findFirst();
        return leaderOptional.map(MapleGuildCharacter::getId).orElse(null);
    }

    /**
     * 向所有成员发送数据包
     */
    public void sendPacket(byte[] packet) {
        for (MapleGuildCharacter member : members) {
            ChannelServerGroup.sendPacket(member.getId(), packet);
        }
    }

    public void clearInvitedAllianceId() {
        this.setInvitedAllianceId(null);
    }

    public boolean isFull() {
        return members.size() == capacity;
    }

    public boolean isMember(Integer playerId) {
        return members.stream().anyMatch(m -> ObjectUtil.equal(m.getId(), playerId));
    }

    public void expelMember(Integer playerId) {
        final Iterator<MapleGuildCharacter> itr = members.iterator();
        while (itr.hasNext()) {
            final MapleGuildCharacter mgc = itr.next();
            if(ObjectUtil.equal(mgc.getId() ,playerId)){
                members.remove(mgc);


                //TODO 需要操作联盟数据
            }
        }
    }

    public String getRanks(int idx){
        return ranks[idx];
    }

    public void setRank(int idx, String rank) {
        ranks[idx] = rank;
    }
}
