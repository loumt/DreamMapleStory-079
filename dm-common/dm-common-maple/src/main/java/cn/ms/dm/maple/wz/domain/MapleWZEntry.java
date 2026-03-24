package cn.ms.dm.maple.wz.domain;

import cn.ms.dm.maple.wz.base.MapleDataEntity;
import cn.ms.dm.maple.wz.base.MapleDataEntry;
import lombok.Getter;

/**
 * @author LouMT
 * @name MapleWZEntry
 * @date 2026-03-18 10:14
 * @email lmtemail163@163.com
 * @description
 */
@Getter
public class MapleWZEntry implements MapleDataEntry {
    /**
     * 文件名称/文件夹名称
     */
    private final String name;

    private final int size;

    private final int checksum;

    private final MapleDataEntity parent;

    private final String path;

    public MapleWZEntry(String name, int size, int checksum, MapleDataEntity parent, String path) {
        super();
        this.name = name;
        this.size = size;
        this.checksum = checksum;
        this.parent = parent;
        this.path = path;
    }
}
