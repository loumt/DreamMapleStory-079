package cn.ms.dm.server.operation.wz;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.utils.StringUtil;
import cn.ms.dm.maple.annotation.WzParser;
import cn.ms.dm.maple.wz.MapleDataProviderFactory;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.maple.wz.base.MapleDataDirectoryEntry;
import cn.ms.dm.maple.wz.base.MapleDataFileEntry;
import cn.ms.dm.maple.wz.base.MapleDataProvider;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ResourceLoader;

/**
 * @author LouMT
 * @name WzExecutor
 * @date 2026-03-18 11:16
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【WZ处理器】")
@Getter
public abstract class WzExecutor {
    @Resource
    private ResourceLoader loader;
    /**
     * 数据加载器
     */
    protected MapleDataProvider provider;
    /**
     * 文件路径
     */
    protected String path;

    /**
     * 初始化加载器
     */
    @SneakyThrows
    @PostConstruct
    protected void initExecutor() {
        WzParser parser = AnnotationUtils.getAnnotation(this.getClass(), WzParser.class);
        if (ObjectUtil.isNull(parser) || StringUtil.isEmpty(parser.value()))
            throw new RuntimeException(this.getClass().getName() + " 缺少 @WzParser注解.");

        String wzName = parser.value();
        org.springframework.core.io.Resource resource = loader.getResource("classpath:/wz/" + wzName);
        if (!resource.exists()) {
            log.error("请检查 {} 文件是否存在", resource.getFilename());
            return;
        }

        this.path = resource.getFile().getAbsolutePath();
        this.provider = MapleDataProviderFactory.getDataProvider(resource.getFile());
        log.info("加载 {} 完成! 文件夹数:【{}】  文件数:【{}】", wzName, provider.getEntry().getDirectories().size(), provider.getEntry().getFiles().size());

        //初始化任务
        init();
    }

    /**
     * 初始化任务
     */
    protected void init() {
    }

    public MapleData getMapleData(String itemId) {
        MapleDataDirectoryEntry root = provider.getEntry();
        MapleData data = researchFileEntry(root, itemId);
        if (ObjectUtil.isNull(data)) log.error("{} 未在 {} 中找到", itemId, path);
        return data;
    }

    private MapleData researchFileEntry(MapleDataDirectoryEntry root, String itemId) {
        for (MapleDataFileEntry file : root.getFiles()) {
            if (file.isItemId(itemId)) return file.getData();
        }
        for (MapleDataDirectoryEntry directory : root.getDirectories()) {
            MapleData mapleData = researchFileEntry(directory, itemId);
            if(mapleData != null) return mapleData;
        }
        return  null;
    }
}
