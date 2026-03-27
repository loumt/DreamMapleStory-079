package cn.ms.dm.server.operation.packet;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.alliance.AllianceOperationEnum;
import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.client.MapleAlliance;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.AlliancePacketCreator;
import cn.ms.dm.server.operation.packet.creator.CharacterPacketCreator;
import cn.ms.dm.server.operation.packet.creator.MaplePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MapleAllianceExecutor
 * @date 2026-02-26 14:04
 * @email lmtemail163@163.com
 * @description 联盟
 */
@Component
@Slf4j(topic = "[MAPLE][Alliance]")
public class AllianceOpExecutor {

    @PacketHandler(ReceivePacketOpcode.ALLIANCE_OPERATION)
    public void handleAlliance(LittleEndianAccessor slea, MapleClient client) {
        Integer guildId = client.getPlayer().getGuildId();
        Integer characterId = client.getPlayer().getPlayerId();
        AllianceOperationEnum operation = AllianceOperationEnum.codeOf(slea.readByte());
        if(operation == null) return;

        if(ObjectUtil.isNull(guildId)) {
            client.sendPacket(CharacterPacketCreator.enableActions());
            return;
        }

        final MapleGuild guild = WorldOperation.Guild.getGuild(guildId);
        if(ObjectUtil.isNull(guild) || ObjectUtil.isNull(guild.getAllianceId())) {
            client.sendPacket(CharacterPacketCreator.enableActions());
            return;
        }

        //需要一些校验
        //1.当op!=1时，非族长不可操作
        GuildRankType guildRank = WorldOperation.Guild.getRank(guildId, characterId);
        if(guildRank != GuildRankType.LEADER && operation != AllianceOperationEnum.LOAD) return;

        MapleAlliance alliance = WorldOperation.Alliance.getAlliance(guild.getAllianceId());
        AllianceRankType allianceRank = WorldOperation.Alliance.getRank(alliance.getId(), characterId);

        switch (operation){
            case LOAD: //获取联盟信息
                handleLoadOperation(client, alliance);
                break;
            case LEAVE: //离开
                handleLeaveOperation(client, alliance);
                break;
            case SEND_INVITE: //发出邀请
                handleSendInviteOperation(client, alliance,  alliance.getId(), allianceRank, slea.readMapleAsciiString());
                break;
            case ACCEPT_INVITE://接收邀请
                handleAcceptInviteOperation(guild);
                break;
            case EXPEL: //驱逐，脱离
                //TODO 检查一下这个数据的格式
                if(slea.available() > 4) {
                    guildId = slea.readInt();
                    if(slea.available() > 4 && ObjectUtil.equal(guild.getAllianceId(), slea.readInt())) break;
                }
                WorldOperation.Alliance.removeGuild(guild.getAllianceId(), guildId);
                break;
            case CHANGE_LEADER://设置联盟长
                if(allianceRank!= AllianceRankType.LEADER) break;
                WorldOperation.Alliance.changeLeader(guild.getAllianceId(), slea.readInt());
                break;
            case UPDATE_TITLE://更新标题
                if(allianceRank!= AllianceRankType.LEADER) break;
                //TODO 这个暂时不知道更新什么东西
                break;
            case CHANGE_RANK: //改变职位
                if(allianceRank != AllianceRankType.LEADER && allianceRank != AllianceRankType.VICE_LEADER) break;
                WorldOperation.Alliance.changeRank(guild.getAllianceId(), slea.readInt(), slea.readByte());
                break;
            case CHANGE_NOTICE://更改通告
                if(allianceRank != AllianceRankType.LEADER && allianceRank != AllianceRankType.VICE_LEADER) break;
                final String notice = slea.readMapleAsciiString();
                if (notice.length() > 100) break;
                WorldOperation.Alliance.updateNotice(guild.getAllianceId(), notice);
                break;
        }
    }

    @PacketHandler(ReceivePacketOpcode.DENY_ALLIANCE_REQUEST)
    public void handleDenyAlliance(LittleEndianAccessor slea, MapleClient client) {
        AllianceOperationEnum operation = AllianceOperationEnum.codeOf(slea.readByte());
        if(operation != AllianceOperationEnum.REJECT_INVITE) return;

        Integer guildId = client.getPlayer().getGuildId();
        Integer characterId = client.getPlayer().getPlayerId();

        final MapleGuild guild = WorldOperation.Guild.getGuild(guildId);
        GuildRankType guildRank = guild.getRank(characterId);
        if(guildRank != GuildRankType.LEADER) return;

        Integer invitedAllianceId = guild.getInvitedAllianceId();
        if(invitedAllianceId == null) return;
        guild.clearInvitedAllianceId();

        Integer allianceLeaderId = WorldOperation.Alliance.getLeaderId(invitedAllianceId);
        ChannelServerGroup.sendPacket(allianceLeaderId, MessagePacketCreator.serverNotice(5, "家族【" + guild.getName() + "】拒绝了您的联盟邀请。"));
    }

    /*************************************************private method***************************************************/
    private void handleAcceptInviteOperation(MapleGuild guild) {
        if(guild.getInvitedAllianceId() == null) return;

        WorldOperation.Alliance.joinGuild(guild.getInvitedAllianceId(), guild);
        WorldOperation.Guild.clearInvitedAllianceId(guild.getId());
    }

    private void handleSendInviteOperation(MapleClient client,
                                           MapleAlliance alliance,
                                           Integer allianceId,
                                           AllianceRankType allianceRank,
                                           String guildName) {
        final Integer inviteGuildLeaderId = WorldOperation.Guild.getLeaderId(guildName);
        if(inviteGuildLeaderId == null) return;
        //判断自身身份是否为联盟长
        if(allianceRank!= AllianceRankType.LEADER) return;
        //判断对方族长是否在线
        MapleCharacter inviteGuildLeader = ChannelServerGroup.getCharacter(inviteGuildLeaderId);
        if(inviteGuildLeader == null) return;
        //判断本联盟是否有空位
        boolean canInvite = WorldOperation.Alliance.canInvite(allianceId);
        if(!canInvite) return;

        inviteGuildLeader.getClient().sendPacket(AlliancePacketCreator.sendAllianceInvite(alliance.getName(), client.getPlayer()));
        WorldOperation.Guild.setInvitedAllianceId(inviteGuildLeader.getGuildId(), allianceId);
    }

    private void handleLeaveOperation(MapleClient client, MapleAlliance alliance) {
        //尉氏县
    }

    private void handleLoadOperation(MapleClient client, MapleAlliance alliance) {
        client.sendPacket(AlliancePacketCreator.getAllianceUpdate(alliance));
    }
}
