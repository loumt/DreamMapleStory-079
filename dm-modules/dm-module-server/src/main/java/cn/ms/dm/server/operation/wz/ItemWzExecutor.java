package cn.ms.dm.server.operation.wz;

import cn.ms.dm.maple.annotation.WzParser;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.maple.wz.base.MapleDataDirectoryEntry;
import cn.ms.dm.maple.wz.base.MapleDataFileEntry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name ItemWzExecutor
 * @date 2026-03-18 13:24
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【WZ-Item】")
@WzParser("Item.wz")
@Component
public class ItemWzExecutor extends WzExecutor{

}