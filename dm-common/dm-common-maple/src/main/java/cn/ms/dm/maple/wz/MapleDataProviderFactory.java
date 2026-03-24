package cn.ms.dm.maple.wz;

import cn.ms.dm.maple.wz.base.MapleDataProvider;
import cn.ms.dm.maple.wz.domain.MapleWZFile;

import java.io.File;

/**
 * @author LouMT
 * @name MapleDataProviderFactory
 * @date 2026-03-17 11:09
 * @email lmtemail163@163.com
 * @description WZ文件工具
 * 在服务器端，wz文件是一个个文件夹
 * 在客户端，wz文件是一个个压缩包
 */
public final class MapleDataProviderFactory {

    private static MapleDataProvider getWZ(File file) {
        return new MapleWZFile(file);
    }

    public static MapleDataProvider getDataProvider(File file) {
        return getWZ(file);
    }
}
