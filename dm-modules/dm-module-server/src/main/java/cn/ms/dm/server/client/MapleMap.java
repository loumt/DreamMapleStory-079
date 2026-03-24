package cn.ms.dm.server.client;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LouMT
 * @name MapleMap
 * @date 2026-02-26 16:33
 * @email lmtemail163@163.com
 * @description 地图场景
 */
@Getter
public final class MapleMap {
    //场景下玩家列表
    private final List<MapleCharacter> characters = new LinkedList<>();

}
