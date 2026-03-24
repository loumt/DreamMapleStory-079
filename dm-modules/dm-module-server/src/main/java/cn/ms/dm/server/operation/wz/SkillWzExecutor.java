package cn.ms.dm.server.operation.wz;

import cn.ms.dm.maple.annotation.WzParser;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name SkillWzExecutor
 * @date 2026-03-18 13:23
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【WZ-Skill】")
@WzParser("Skill.wz")
@Component
public class SkillWzExecutor extends WzExecutor{



}