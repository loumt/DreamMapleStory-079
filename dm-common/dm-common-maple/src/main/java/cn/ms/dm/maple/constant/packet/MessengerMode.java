package cn.ms.dm.maple.constant.packet;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 聊天室指令类型
 */
@Getter
@AllArgsConstructor
public enum MessengerMode {
    OPEN((byte)0x00, "打开"),
    EXIT((byte)0x02, "离开"),
    INVITE((byte)0x03, "邀请"),
    DECLINE((byte)0x05, "拒绝"),
    MESSAGE((byte)0x06, "消息"),
    ;

    private final byte mode;
    private final String type;

    public static MessengerMode modeOf(byte mode){
        for (MessengerMode value : MessengerMode.values()) {
            if(value.mode == mode) return value;
        }
        return null;
    }
}