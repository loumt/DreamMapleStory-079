package cn.ms.dm.server.operation.packet;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.domain.guild.Guild;
import cn.ms.dm.domain.guild.GuildRank;
import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.guild.GuildOperationEnum;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.maple.constant.map.MapId;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.client.MapleGuildCharacter;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.database.service.GuildInviteService;
import cn.ms.dm.server.database.service.GuildRankService;
import cn.ms.dm.server.database.service.GuildService;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.GuildPacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;


/**
 * @author LouMT
 * @name MapleGuildExecutor
 * @date 2026-02-26 13:59
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "[MAPLE][GUILD]")
@Component
@RequiredArgsConstructor
public class GuildOpExecutor {
    private final GuildService guildService;
    private final GuildRankService guildRankService;
    private final GuildInviteService guildInviteService;

    @PacketHandler(ReceivePacketOpcode.GUILD_OPERATION)
    public void handleGuildOperation(LittleEndianAccessor slea, MapleClient client) {
        GuildOperationEnum operation = GuildOperationEnum.codeOf(slea.readByte());
        if(operation == null) return;

        MapleCharacter player = client.getPlayer();
        switch (operation){
            case CREATE:
                handleCreateOperation(client, player, slea.readMapleAsciiString());
                break;
            case INVITE:
                handleInviteOperation(client, slea.readMapleAsciiString());
                break;
            case ACCEPTED:
                handleAcceptedOperation(client, slea.readInt(), slea.readInt());
                break;
            case LEAVING:
                handleLeaveOperation(client, slea.readInt(), slea.readMapleAsciiString());
                break;
            case EXPEL:
                handleExpelOperation(client, slea.readInt());
                break;
            case CHANGE_RANK_TITLE:
                handleChangeRankTitleOperation();
                break;
            case CHANGE_RANK:
                handleChangeRankOperation();
                break;
            case CHANGE_EMBLEM:
                handleChangeEmblemOperation();
                break;
            case CHANGE_NOTICE:
                handleChangeNoticeOperation();
                break;
        }
    }

    @PacketHandler(ReceivePacketOpcode.DENY_GUILD_REQUEST)
    public void handleDenyGuildRequest(LittleEndianAccessor slea, MapleClient client) {

    }


    /*************************************************private method***************************************************/

    /**
     * 创建家族
     * @param client client
     * @param player 玩家
     * @param guildName 家族名字
     */
    private void handleCreateOperation(MapleClient client,final MapleCharacter player,final String guildName) {
        if(player.getGuildId() != null) {
            client.sendMessage("无法建立公会,已经有公会了.");
            return;
        }
        if(MapId.MAP_200000301.isMap(player.getMapId())){
            client.sendMessage("无法建立公会,不在英雄之殿.");
            return;
        }
        int cost = AppConfigProperties.GUILD_CREATE_COST;
        if(player.getMeso() < cost) {
            client.sendMessage("你没有足够的金币创建家族,目前需要"+ cost +" 的金币。");
            return;
        }

        if(guildName.length() > 15 || guildName.length() <= 3) {
            client.sendMessage("这个名字不被允许.");
            return;
        }
        if(guildService.exists(Wrappers.lambdaQuery(Guild.class).eq(Guild::getName, guildName))){
            client.sendPacket(GuildPacketCreator.genericGuildMessage((byte) 0x1c));
            return;
        }

        //创建家族
        Integer guildId = guildService.createGuild(guildName, player.getPlayerId().longValue()).intValue();
        //扣除金币
        player.gainMeso(-cost);
        //家族属性设置
        player.setGuildId(guildId);
        player.setGuildName(guildName);
        //家族成员属性
        MapleGuildCharacter gc = new MapleGuildCharacter();
        gc.setName(player.getName());
        gc.setId(player.getPlayerId());
        gc.setLevel(player.getLevel());
        gc.setRank(GuildRankType.LEADER);
        player.setMgc(gc);
        //刷新缓存
        WorldOperation.Guild.refresh(guildId);
        //展示家族信息
        client.sendPacket(GuildPacketCreator.showGuildInfo(player));
        //通知
        client.sendMessage("公会【"+ guildName +"】已创建.");
    }


    /**
     * 接收家族邀请
     * @param client
     * @param guildId 邀请家族ID
     * @param playerId 被邀请玩家ID
     */
    private void handleAcceptedOperation(MapleClient client, Integer guildId, Integer playerId) {
        if(ObjectUtil.isNull(client.getPlayer().getGuildId())) return;
        if(!ObjectUtil.equal(playerId, client.getPlayer().getPlayerId())) return;

        boolean result = guildInviteService.acceptInvite(guildId, playerId);
        if(!result) return;

        MapleGuild guild = WorldOperation.Guild.getGuild(guildId);
        if(guild.isFull()){
            client.sendMessage("该公会已经满员了.");
            return;
        }

        GuildRank rank = new GuildRank();
        rank.setCharacterId(client.getPlayer().getPlayerId().longValue());
        rank.setRank(GuildRankType.MEMBER);
        rank.setGuildId(guildId.longValue());
        rank.setJoinTime(LocalDateTime.now());
        guildRankService.save(rank);

        //家族属性设置
        client.getPlayer().setGuildId(guildId);
        client.getPlayer().setGuildName(guild.getName());
        //家族成员属性
        MapleGuildCharacter gc = new MapleGuildCharacter();
        gc.setName(client.getPlayer().getName());
        gc.setId(client.getPlayer().getPlayerId());
        gc.setLevel(client.getPlayer().getLevel());
        gc.setRank(GuildRankType.MEMBER);
        client.getPlayer().setMgc(gc);
        //刷新缓存
        WorldOperation.Guild.refresh(guildId);
        //展示家族信息
        client.sendPacket(GuildPacketCreator.showGuildInfo(client.getPlayer()));
        //通知
        client.sendMessage("加入【"+ guild.getName() +"】成功");
    }

    /**
     * 离开家族
     * @param client
     * @param playerId 玩家ID
     * @param playerName 玩家名称
     */
    private void handleLeaveOperation(MapleClient client, Integer playerId, String playerName) {
        if(!ObjectUtil.equal(playerId, client.getPlayer().getPlayerId())) return;
        if(!ObjectUtil.equal(client.getPlayer().getName(), playerName)) return;
        if(ObjectUtil.isNull(client.getPlayer().getGuildId())) return;

        WorldOperation.Guild.leave(client.getPlayer().getGuildId(), playerId);
        client.sendPacket(GuildPacketCreator.showGuildInfo());
    }


    /**
     * 驱逐
     * @param client
     * @param playerId 目标玩家ID
     */
    private void handleExpelOperation(MapleClient client, Integer playerId) {
        Integer guildId = client.getPlayer().getGuildId();
        if(guildId == null) return;

        GuildRankType guildRank = WorldOperation.Guild.getRank(client.getPlayer().getGuildId(), client.getPlayer().getPlayerId());
        //族长-副族长
        if(guildRank != GuildRankType.LEADER && guildRank != GuildRankType.VICE_LEADER) return;
        //判断是否为族员
        if(!WorldOperation.Guild.isMember(guildId, playerId)) return;
        //向目标对象发送提醒
        MapleCharacter character = ChannelServerGroup.getCharacter(playerId);
        if(ObjectUtil.isNotNull(character)){
            character.sendMessage("您已经被家族踢出.");
        }
        WorldOperation.Guild.expelMember(guildId, playerId);
    }

    private void handleChangeRankOperation() {

    }

    private void handleChangeEmblemOperation() {

    }

    private void handleChangeNoticeOperation() {

    }

    private void handleInviteOperation(MapleClient client, String playerName) {
        Integer guildId = client.getPlayer().getGuildId();
        if(guildId == null) return;

        GuildRankType guildRank = WorldOperation.Guild.getRank(client.getPlayer().getGuildId(), client.getPlayer().getPlayerId());
        //族长-副族长
        if(guildRank != GuildRankType.LEADER && guildRank != GuildRankType.VICE_LEADER) return;

        MapleCharacter invitePlayer = ChannelServerGroup.getCharacter(playerName);
        if(invitePlayer == null) {
            client.sendPacket(GuildPacketCreator.genericGuildMessage((byte) 0x2a));
            return;
        }
        if(ObjectUtil.isNotNull(invitePlayer.getGuildId())){
            client.sendPacket(GuildPacketCreator.genericGuildMessage((byte) 0x28));
            return;
        }

        invitePlayer.sendPacket(GuildPacketCreator.guildInvite(client.getPlayer().getGuildId(), client.getPlayer().getName()));
        guildInviteService.createInvite(client.getPlayer().getPlayerId(), guildId, invitePlayer.getPlayerId());
    }

    private void handleChangeRankTitleOperation() {

    }
}
