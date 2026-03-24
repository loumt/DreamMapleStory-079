package cn.ms.dm.server.operation;

import cn.ms.dm.core.utils.SpringUtils;
import cn.ms.dm.server.operation.wz.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LouMT
 * @name WzOperation
 * @date 2026-03-17 10:36
 * @email lmtemail163@163.com
 * @description 操作Wz文件
 */
@Slf4j
public class WzOperation {
    public static CharacterWzExecutor Character;
    public static EtcWzExecutor Etc;
    public static ItemWzExecutor Item;
    public static MapWzExecutor Map;
    public static MobWzExecutor Mob;
    public static QuestWzExecutor Quest;
    public static ReactorWzExecutor Reactor;
    public static SkillWzExecutor Skill;
    public static StringWzExecutor String;

    public static void init() {
        Character = SpringUtils.getBean(CharacterWzExecutor.class);
        Etc = SpringUtils.getBean(EtcWzExecutor.class);
        Item = SpringUtils.getBean(ItemWzExecutor.class);
        Map = SpringUtils.getBean(MapWzExecutor.class);
        Mob = SpringUtils.getBean(MobWzExecutor.class);
        Quest = SpringUtils.getBean(QuestWzExecutor.class);
        Reactor = SpringUtils.getBean(ReactorWzExecutor.class);
        Skill = SpringUtils.getBean(SkillWzExecutor.class);
        String = SpringUtils.getBean(StringWzExecutor.class);
    }
}
