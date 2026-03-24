package cn.ms.dm.maple.base;

/**
 * @author LouMT
 * @name AbstractAnimatedMapleMapObject
 * @date 2026-02-27 15:48
 * @email lmtemail163@163.com
 * @description
 */
public abstract class AbstractAnimatedMapleMapObject extends AbstractMapleMapObject implements AnimatedMapleMapObject {
    /**
     * 姿势
     */
    private int stance;

    @Override
    public int getStance() {
        return stance;
    }

    @Override
    public void setStance(int stance) {
        this.stance = stance;
    }

    @Override
    public boolean isFacingLeft() {
        return getStance() % 2 != 0;
    }

    public int getFacingDirection() {
        return getStance() % 2;
    }
}
