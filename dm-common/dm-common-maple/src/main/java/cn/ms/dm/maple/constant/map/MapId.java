package cn.ms.dm.maple.constant.map;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name MapIdConstant
 * @date 2026-03-26 16:34
 * @email lmtemail163@163.com
 * @description 地图ID集合
 */
@Getter
@AllArgsConstructor
public enum MapId {
    MAP_200000301(200000301, "英雄之殿"),

    ;
    private final int code;
    private final String map;

    public boolean isMap(Integer mapId){
        return code == mapId;
    }
}
