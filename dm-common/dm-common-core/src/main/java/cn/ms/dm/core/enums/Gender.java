package cn.ms.dm.core.enums;

import cn.ms.dm.core.base.BaseEnum;
import lombok.AllArgsConstructor;

/**
 * @author LouMT
 * @name Gender
 * @date 2026-03-06 15:37
 * @email lmtemail163@163.com
 * @description 性别
 */
@AllArgsConstructor
public enum Gender implements BaseEnum {
    UNKNOWN(-1),
    MALE(0),
    FEMALE(1);

    private final int code;

    @Override
    public int getCode() {
        return code;
    }

    public static Gender codeOf(byte code){
        for (Gender value : Gender.values()) {
            if(value.code == code) return value;
        }
        return null;
    }
}
