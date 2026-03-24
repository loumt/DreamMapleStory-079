package cn.ms.dm.maple.wz.domain;

import cn.ms.dm.maple.wz.base.MapleDataDirectoryEntry;
import cn.ms.dm.maple.wz.base.MapleDataEntity;
import cn.ms.dm.maple.wz.base.MapleDataEntry;
import cn.ms.dm.maple.wz.base.MapleDataFileEntry;
import lombok.Getter;

import java.util.*;

/**
 * @author LouMT
 * @name MapleWZDirectoryEntry
 * @date 2026-03-18 10:10
 * @email lmtemail163@163.com
 * @description
 */
public class MapleWZDirectoryEntry extends MapleWZEntry implements MapleDataDirectoryEntry {
    /**
     * 子文件夹
     */
    private final List<MapleDataDirectoryEntry> directories = new ArrayList<>();
    /**
     * 子文件
     */
    private final List<MapleDataFileEntry> files = new ArrayList<>();
    /**
     * 子文件 + 子文件夹
     */
    private final Map<String, MapleDataEntry> entries = new HashMap<>();

    public MapleWZDirectoryEntry() {
        super(null, 0, 0, null, null);
    }

    public MapleWZDirectoryEntry(String name) {
        super(name, 0, 0, null, null);
    }

    public MapleWZDirectoryEntry(String name, int size, int checksum) {
        super(name, size, checksum, null, null);
    }

    public MapleWZDirectoryEntry(String name, int size, int checksum, MapleDataEntity parent, String path) {
        super(name, size, checksum, parent, path);
    }

    public List<MapleDataDirectoryEntry> getDirectories() {
        return Collections.unmodifiableList(directories);
    }

    public List<MapleDataFileEntry> getFiles() {
        return Collections.unmodifiableList(files);
    }

    public MapleDataEntry getEntry(String name) {
        return entries.get(name);
    }


    public void addDirectory(MapleDataDirectoryEntry dir) {
        directories.add(dir);
        entries.put(dir.getName(), dir);
    }

    public void addFile(MapleDataFileEntry fileEntry) {
        files.add(fileEntry);
        entries.put(fileEntry.getName(), fileEntry);
    }

}
