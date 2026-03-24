package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MaplePlayerStatus
 * @date 2026-03-09 17:00
 * @email lmtemail163@163.com
 * @description 角色/玩家状态
 */
@Getter
@Setter
public class MaplePlayerStatus implements Serializable {
    @Serial
    private static final long serialVersionUID = 118792219697382361L;
    //属性
    private Integer strength, dexterity, luck, intelligence;
    //玩家状态
    private Integer hp, maxHp, mp, maxMp;

}
