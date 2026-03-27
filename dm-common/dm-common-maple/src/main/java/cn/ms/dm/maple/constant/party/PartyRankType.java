package cn.ms.dm.maple.constant.party;

import cn.ms.dm.core.base.BaseEnum;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name PartyRankType
 * @date 2026-03-24 15:37
 * @email lmtemail163@163.com
 * @description
 */
@AllArgsConstructor
@Getter
public enum PartyRankType {
    LEADER(1, "组长"),
    MEMBER(5, "成员")
    ;

    @EnumValue
    private final int code;
    private final String type;
}