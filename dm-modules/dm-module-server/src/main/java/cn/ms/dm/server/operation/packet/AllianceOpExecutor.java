package cn.ms.dm.server.operation.packet;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.client.MapleGuild;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.CharacterPacketCreator;
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
        Long guildId = client.getCharacter().getGuildId();
        if(ObjectUtil.isNull(guildId)) {
            client.sendPacket(CharacterPacketCreator.enableActions());
            return;
        }

        final MapleGuild guild = WorldOperation.Guild.getGuild(guildId);
        if(ObjectUtil.isNull(guild)) {
            client.sendPacket(CharacterPacketCreator.enableActions());
            return;
        }

        byte op = slea.readByte();

        if(op == 22) {//拒绝
            Long inviteId = WorldOperation.Guild.getInviteId(client.getCharacter().getGuildId());



        }

    }

    @PacketHandler(ReceivePacketOpcode.DENY_ALLIANCE_REQUEST)
    public void handleDenyAlliance(LittleEndianAccessor slea, MapleClient client) {

    }
}
