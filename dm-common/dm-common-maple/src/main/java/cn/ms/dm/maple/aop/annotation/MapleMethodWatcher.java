package cn.ms.dm.maple.aop.annotation;

import cn.ms.dm.core.utils.StringUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法监测
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapleMethodWatcher {
    String value() default StringUtil.EMPTY;
}
