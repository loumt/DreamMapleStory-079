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
    LEADER(0, "族长"),
    VICE_LEADER(1, "副族长"),
    ELDER(2, "长老"),
    ELITE(3, "精英"),
    MEMBER(4, "成员")
    ;

    @EnumValue
    private final int code;
    private final String title;

}
