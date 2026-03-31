package cn.ms.dm.maple.constant.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @description PARTYCHAT指令数据包中的类型标识
 * @date 2026/2/28 15:15
 */
@Getter
@AllArgsConstructor
public enum ChatType {
    BUDDY(0, "好友聊天"),
    PARTY(1, "队伍聊天"),
    GUILD(2, "家族聊天"),
    ALLIANCE(3, "联盟聊天"),
    ;

    private final int mode;
    private final String type;

    public static ChatType modeOf(int mode){
        for (ChatType value : ChatType.values()) {
            if(value.mode == mode) return value;
        }
        return null;
    }
}
