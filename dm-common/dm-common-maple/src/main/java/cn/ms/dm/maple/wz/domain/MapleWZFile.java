package cn.ms.dm.maple.wz.domain;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.maple.wz.base.MapleDataDirectoryEntry;
import cn.ms.dm.maple.wz.base.MapleDataProvider;
import cn.ms.dm.maple.wz.domain.xml.MapleXMLData;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author LouMT
 * @name MapleWZFile
 * @date 2026-03-18 10:10
 * @email lmtemail163@163.com
 * @description 代表一个WZ文件
 */
@Getter
@Slf4j(topic = "【WZ文件解析】")
public class MapleWZFile implements MapleDataProvider {
    /**
     * wz文件信息
     */
    private final File root;
    /**
     * 下层文件信息
     */
    private final MapleWZDirectoryEntry entry;

    public MapleWZFile(File fileIn) {
        root = fileIn;
        entry = new MapleWZDirectoryEntry(fileIn.getName());
        fillMapleDataEntity(root, entry);
    }

    /**
     * 递归获取文件信息
     */
    private void fillMapleDataEntity(File r, MapleWZDirectoryEntry wz) {
        File[] files = r.listFiles();
        if(ArrayUtil.isEmpty(files)) return;

        for (File file : files) {
            String fileName = file.getName();

            if (file.isDirectory() && !fileName.endsWith(".img")) {
                //文件夹处理
                MapleWZDirectoryEntry newDir = new MapleWZDirectoryEntry(fileName, 0, 0, wz, file.getAbsolutePath());
                wz.addDirectory(newDir);
                fillMapleDataEntity(file, newDir);
            } else if (fileName.endsWith(".xml")) {
                //*****.img.xml文件处理
                MapleWZFileEntry mapleWZFileEntry = new MapleWZFileEntry(fileName, 0, 0, wz, file.getAbsolutePath());
                wz.addFile(mapleWZFileEntry);
            }
        }
    }

    @Override
    public MapleData getData(String itemId) {
        File dataFile = new File(root, itemId + ".img.xml");

        FileInputStream fis;
        try {
            fis = new FileInputStream(dataFile);
        } catch (FileNotFoundException e) {
            log.error("{} 中不存在 {} 文件", root.getAbsolutePath(), itemId);
            throw new RuntimeException("DataFile " + itemId + " does not exist in " + root.getAbsolutePath());
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
