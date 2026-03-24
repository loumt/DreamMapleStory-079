package cn.ms.dm.maple.base;

import cn.ms.dm.maple.constant.map.MapleMapObjectType;

import java.awt.*;

/**
 * @author LouMT
 * @name AbstractMapleMapObject
 * @date 2026-02-27 15:45
 * @email lmtemail163@163.com
 * @description
 */
public abstract class AbstractMapleMapObject implements MapleMapObject{
    //位置点
    private final Point position = new Point();
    //对象ID
    private Long objectId;

    @Override
    public abstract MapleMapObjectType getType();

    @Override
    public Point getPosition() {
        return new Point(position);
    }

    @Override
    public void setPosition(Point position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    @Override
    public Long getObjectId() {
        return objectId;
    }

    @Override
    public void setObjectId(Long id) {
        this.objectId = id;
    }

}
