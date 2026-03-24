package cn.ms.dm.maple.annotation;

import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LouMT
 * @name WzParser
 * @date 2026-03-19 14:09
 * @email lmtemail163@163.com
 * @description
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WzParser {
    /**
     * wz文件名
     */
    String value();
}
