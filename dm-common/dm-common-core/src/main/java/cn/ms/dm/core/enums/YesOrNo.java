package cn.ms.dm.core.enums;


import cn.ms.dm.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YesOrNo implements BaseEnum {
    YES(1, "是"),
    NO(0, "否");

    private final int code;
    private final String msg;
}
