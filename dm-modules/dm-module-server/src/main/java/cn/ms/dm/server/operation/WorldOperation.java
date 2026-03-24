package cn.ms.dm.server.operation;

import cn.ms.dm.core.utils.SpringUtils;
import cn.ms.dm.server.operation.world.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LouMT
 * @name WorldOperation
 * @date 2026-03-03 15:30
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j
public final class WorldOperation {
    //好友
    public static BuddyExecutor Buddy;
    //组队
    public static PartyExecutor Party;
    //家族
    public static FamilyExecutor Family;
    //公会
    public static GuildExecutor Guild;
    //联盟
    public static AllianceExecutor Alliance;
    //快递
    public static MessengerExecutor Messenger;
    //广播
    public static BroadcastExecutor Broadcast;
    //查询器
    public static FindExecutor Find;
    //物品清单
    public static ItemExecutor Item;

    public static void init() {
        Buddy = SpringUtils.getBean(BuddyExecutor.class);
        Party = SpringUtils.getBean(PartyExecutor.class);
        Family = SpringUtils.getBean(FamilyExecutor.class);
        Guild = SpringUtils.getBean(GuildExecutor.class);
        Alliance = SpringUtils.getBean(AllianceExecutor.class);
        Messenger = SpringUtils.getBean(MessengerExecutor.class);
        Broadcast = SpringUtils.getBean(BroadcastExecutor.class);
        Find = SpringUtils.getBean(FindExecutor.class);
        Item = SpringUtils.getBean(ItemExecutor.class);
    }
}
