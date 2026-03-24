package cn.ms.dm.maple.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MapleType {
    UNKNOWN(-1, "UTF-8"),
    한국(1, "EUC_KR"),
    日本(3, "Shift_JIS"),
    CHINA(4, "GBK"),
    SEA(7, "UTF-8"),
    GLOBAL(8, "UTF-8"),
    BRAZIL(9, "UTF-8");

    private final int type;
    private final String ANSI;

    public static MapleType getDefault(){
        return MapleType.CHINA;
    }
}
