package cn.ms.dm.maple.constant.guild;

import cn.ms.dm.core.base.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name GuildRankType
 * @date 2026-03-24 14:50
 * @email lmtemail163@163.com
 * @description 家族职级
 */
@AllArgsConstructor
@Getter
public enum GuildRankType {
    LEADER(1, "族长"),
    VICE_LEADER(2, "副族长"),
    ELDER(3, "长老"),
    ELITE(4, "精英"),
    MEMBER(5, "成员")
    ;

    @EnumValue
    private final int code;
    private final String type;

}
