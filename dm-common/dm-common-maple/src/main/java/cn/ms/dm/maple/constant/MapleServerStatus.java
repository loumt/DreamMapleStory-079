package cn.ms.dm.maple.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务器状态
 */
@Getter
@AllArgsConstructor
public enum MapleServerStatus {
    NORMAL(0,"Normal, 正常状态"),
    HIGHLY_POPULATED(1, "Highly populated, 高占用率"),
    FULL(2, "Full, 已经满了")
    ;

    private final int code;
    private final String desc;
}
