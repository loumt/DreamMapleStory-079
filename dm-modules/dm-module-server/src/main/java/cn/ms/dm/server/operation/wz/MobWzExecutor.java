package cn.ms.dm.server.operation.wz;

import cn.ms.dm.maple.annotation.WzParser;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name MobExecutor
 * @date 2026-03-18 11:11
 * @email lmtemail163@163.com
 * @description 怪物信息
 * 图片信息(info)
 * 移动动画(move)
 * 站立动画(stand)
 * 攻击动画(attack1(包含玩家被击中特效hit信息), attack2....)
 * 被击中动画(hit1)
 * 死亡动画(die1)
 */
@Slf4j(topic = "【WZ-Mob】")
@WzParser("Mob.wz")
@Component
public class MobWzExecutor extends WzExecutor{



}