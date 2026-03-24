package cn.ms.dm.maple.wz.domain;

import cn.hutool.core.util.StrUtil;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.maple.wz.base.MapleDataEntity;
import cn.ms.dm.maple.wz.base.MapleDataFileEntry;
import cn.ms.dm.maple.wz.domain.xml.MapleXMLData;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author LouMT
 * @name MapleWZFileEntry
 * @date 2026-03-18 10:11
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@Setter
@Slf4j
public class MapleWZFileEntry extends MapleWZEntry implements MapleDataFileEntry {
    /**
     * 对应代码 Id
     */
    private String itemId;

    /**
     * 对应img文件ID
     * 即： itemId + ".img"
     */
    private String imgName;

    public MapleWZFileEntry(String name, int size, int checksum, MapleDataEntity parent, String path) {
        super(name, size, checksum, parent, path);
        this.imgName = StrUtil.removeSuffix(name, ".xml");
        this.itemId = StrUtil.removeSuffix(imgName, ".img");
    }

    @Override
    public boolean isItemId(String itemId) {
        return StrUtil.equals(this.getItemId(), itemId);
    }

    @Override
    public MapleData getData() {
        String path = this.getPath();
        File dataFile = new File(path);

        FileInputStream fis;
        try {
            fis = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            log.error("不存在 {} 文件", path);
            throw new RuntimeException("DataFile " + path + " does not exists.");
        }
        final MapleXMLData domMapleData;
        try {
            domMapleData = new MapleXMLData(fis, dataFile.getParentFile());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return domMapleData;
    }
}
