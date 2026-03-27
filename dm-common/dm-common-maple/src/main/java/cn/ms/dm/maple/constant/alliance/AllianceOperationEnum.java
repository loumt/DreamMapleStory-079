package cn.ms.dm.maple.constant.alliance;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 联盟操作码
 * 操作码 1:加载 2:离开 3:发出邀请 4:接收邀请 6:驱逐 7:改变领导人 8:更新标题 9:改变职位 10:更改通知 22:拒绝邀请
 */
@Getter
@AllArgsConstructor
public enum AllianceOperationEnum {
    LOAD((byte)0x01, "加载信息"),
    LEAVE((byte)0x02, "离开联盟"),
    SEND_INVITE((byte)0x03, "发出邀请"),
    ACCEPT_INVITE((byte)0x04, "接受邀请"),
    EXPEL((byte)0x06, "驱逐"),
    CHANGE_LEADER((byte)0x07, "更换联盟长"),
    UPDATE_TITLE((byte)0x08, "更换标题"),
    CHANGE_RANK((byte)0x09, "更换职位"),
    CHANGE_NOTICE((byte)0x0A, "更改通知"),
    REJECT_INVITE((byte)0xA6, "拒绝邀请"),
    ;

    private final byte code;
    private final String type;

    public static AllianceOperationEnum codeOf(final byte code) {
        for (AllianceOperationEnum op : AllianceOperationEnum.values()) {
            if (op.code == code) {
                return op;
            }
        }
        return null;
    }
}
