package cn.ms.dm.maple.base;

import cn.ms.dm.maple.constant.map.MapleMapObjectType;

import java.awt.*;

/**
 * @author LouMT
 * @name MapleMapObject
 * @date 2026-02-27 15:41
 * @email lmtemail163@163.com
 * @description 地图对象
 */
public interface MapleMapObject {
    /**
     * 获取对象ID
     */
    Long getObjectId();
    void setObjectId(final Long id);

    /**
     * 对象类别
     */
    MapleMapObjectType getType();

    /**
     * 获取位置信息
     */
    Point getPosition();
    void setPosition(final Point position);

}
