package cn.ms.dm.server.operation;

import cn.ms.dm.core.utils.SpringUtils;
import cn.ms.dm.server.operation.cache.EquipCacheExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LouMT
 * @name CacheOperation
 * @date 2026-03-18 15:50
 * @email lmtemail163@163.com
 * @description WZ固定数据缓存
 */
@Slf4j
public class CacheOperation {
    public static EquipCacheExecutor Equip;

    public static void init() {
        Equip = SpringUtils.getBean(EquipCacheExecutor.class);



    }
}
