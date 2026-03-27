package cn.ms.dm.maple.annotation;

import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.maple.constant.packet.ReceivePacketOpcode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LouMT
 * @name CommandHandler
 * @date 2026-02-26 14:57
 * @email lmtemail163@163.com
 * @description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {
    /**
     * 指令枚举
     */
    ReceivePacketOpcode value();

    /**
     * 备注
     */
    String remark() default StringUtil.EMPTY;

}
