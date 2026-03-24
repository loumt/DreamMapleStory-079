package cn.ms.dm.maple.constant.account;

import cn.ms.dm.core.base.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name VipLevel
 * @date 2026-02-27 14:10
 * @email lmtemail163@163.com
 * @description
 */
@AllArgsConstructor
@Getter
public enum VipLevel implements BaseEnum {
    NORMAL(0, "普通用户"),
    VIP(1, "VIP用户")
    ;

    private final int code;
    private final String type;
}
