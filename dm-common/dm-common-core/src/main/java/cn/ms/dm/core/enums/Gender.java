package cn.ms.dm.core.enums;

import cn.ms.dm.core.base.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name Gender
 * @date 2026-03-06 15:37
 * @email lmtemail163@163.com
 * @description 性别
 */
@Getter
@AllArgsConstructor
public enum Gender{
    UNKNOWN(-1),
    MALE(0),
    FEMALE(1);

    @EnumValue
    private final int code;

    public static Gender codeOf(byte code){
        for (Gender value : Gender.values()) {
            if(value.code == code) return value;
        }
        return null;
    }
}
