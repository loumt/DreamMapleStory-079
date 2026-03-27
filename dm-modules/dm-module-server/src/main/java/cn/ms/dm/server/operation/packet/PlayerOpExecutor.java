package cn.ms.dm.server.operation.packet;

import cn.ms.dm.maple.annotation.PacketHandler;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.server.client.MapleClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MaplePlayerExecutor
 * @date 2026-02-26 10:00
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Slf4j(topic = "[MAPLE][PLAYER]")
public class PlayerOpExecutor {

    /**
     * 角色移动
     */
    @PacketHandler(ReceivePacketOpcode.MOVE_PLAYER)
    public void handleMovePlayer(LittleEndianAccessor slea, MapleClient client) {

    }

    /**
     * 获取角色信息
     */
    @PacketHandler(ReceivePacketOpcode.CHAR_INFO_REQUEST)
    public void handleCharacterInfoRequest(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.CLOSE_RANGE_ATTACK)
    public void handleCloseRangeAttack(LittleEndianAccessor slea, MapleClient client) {

    }
    @PacketHandler(ReceivePacketOpcode.RANGED_ATTACK)
    public void handleRangeAttack(LittleEndianAccessor slea, MapleClient client) {

    }
    @PacketHandler(ReceivePacketOpcode.MAGIC_ATTACK)
    public void handleMagicAttack(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.SPECIAL_MOVE)
    public void handleSpecialMove(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.TAKE_DAMAGE)
    public void handleTakeDamage(LittleEndianAccessor slea, MapleClient client) {

    }
    @PacketHandler(ReceivePacketOpcode.HEAL_OVER_TIME)
    public void handleHeal(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.CANCEL_BUFF)
    public void handleCancelBuff(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.CANCEL_ITEM_EFFECT)
    public void handleCancelItemEffect(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.USE_CHAIR)
    public void handleUseChair(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.SHOW_EXP_CHAIR)
    public void handleShowExpChair(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.CANCEL_CHAIR)
    public void handleCancelChair(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.SKILL_EFFECT)
    public void handleSkillEffect(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.MESO_DROP)
    public void handleMesoDrop(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.USE_DOOR)
    public void handleUseDoor(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.DAMAGE_REACTOR)
    public void handleDamageReactor(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.TOUCH_REACTOR)
    public void handleTouchReactor(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.CLOSE_CHALKBOARD)
    public void handleCloseChalkBoard(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.UPDATE_CHAR_INFO)
    public void handleUpdateCharacterInfo(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.FOLLOW_REQUEST)
    public void handleFollowRequest(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.FOLLOW_REPLY)
    public void handleFollowReply(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.RING_ACTION)
    public void handleRingAction(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.ITEM_UNLOCK)
    public void handleUnlockItem(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.SOLOMON)
    public void handleSolomon(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.GACH_EXP)
    public void handleGachExp(LittleEndianAccessor slea, MapleClient client) {

    }

    @PacketHandler(ReceivePacketOpcode.SPECIAL_ATTACK)
    public void handleSpecialAttack(LittleEndianAccessor slea, MapleClient client) {

    }
}
