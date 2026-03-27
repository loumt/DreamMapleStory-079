package cn.ms.dm.maple.constant.guild;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuildOperationEnum {
    CREATE((byte) 0x2, "建立公会"),
    INVITE((byte) 0x5, "家族邀请"),
    ACCEPTED((byte) 0x6, "接收家族邀请"),
    LEAVING((byte) 0x7, "离开家族"),
    EXPEL((byte) 0x8, "驱逐家族"),
    CHANGE_RANK_TITLE((byte) 0xD, "设置职位名称"),
    CHANGE_RANK((byte) 0xE, "设置职位"),
    CHANGE_EMBLEM((byte) 0xF, "设置家族族徽"),
    CHANGE_NOTICE((byte) 0x10, "更新通知");
    ;

    private final byte code;
    private final String type;


    public static GuildOperationEnum codeOf(final byte code) {
        for (GuildOperationEnum op : GuildOperationEnum.values()) {
            if (op.code == code) {
                return op;
            }
        }
        return null;
    }
}
