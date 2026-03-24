package cn.ms.dm.maple.aop;

import cn.ms.dm.maple.aop.annotation.MapleMethodWatcher;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * @author LouMT
 * @name MethodWatcherAspect
 * @date 2026-03-13 9:44
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【冒险岛服务耗时】")
@Aspect
@Component
public class MethodWatcherAspect {
    @Around("@annotation(cn.ms.dm.maple.aop.annotation.MapleMethodWatcher)")
    public Object handleLog(ProceedingJoinPoint point) throws Throwable {
        Class<?> targetClass = point.getTarget().getClass();
        Method method = ClassUtils.getMethod(targetClass, point.getSignature().getName());
        MapleMethodWatcher watcher = AnnotationUtils.findAnnotation(method, MapleMethodWatcher.class);
        String message = watcher != null ? watcher.value() : point.getSignature().getName();
        Stopwatch started = Stopwatch.createStarted();
        Object proceed = point.proceed();
        log.info("{} COMPLETE, COST => {}", message, started.stop());
        return proceed;
    }
}
