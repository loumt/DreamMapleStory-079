package cn.ms.dm.server.operation.wz;

import cn.ms.dm.maple.annotation.WzParser;
import cn.ms.dm.maple.wz.MapleDataTool;
import cn.ms.dm.maple.wz.base.*;
import cn.ms.dm.server.operation.WzOperation;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name CharacterWzExecutor
 * @date 2026-03-18 13:22
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【WZ-Character】")
@WzParser("Character.wz")
@Component
public class CharacterWzExecutor extends WzExecutor{


}