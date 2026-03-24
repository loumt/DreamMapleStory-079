package cn.ms.dm.server.client;

import cn.ms.dm.maple.base.IMapleEquip;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleEquip
 * @date 2026-03-16 14:23
 * @email lmtemail163@163.com
 * @description 物品-武器
 */
@Getter
@Setter
public class MapleEquip extends MapleItem implements IMapleEquip, Serializable {
    @Serial
    private static final long serialVersionUID = -7295027645097974484L;
    /*************************************基本**************************************/
    //攻击次数
    private Integer attackSpeed;
    //升级次数
    private Integer tuc;
    //击退几率
    private Integer knockback;
    /*************************************需求**************************************/
    private Integer reqLevel;
    private Integer reqJob;
    private Integer reqSTR;
    private Integer reqDEX;
    private Integer reqINT;
    private Integer reqLUK;
    /***********************************属性增加**************************************/
    private Integer incSTR;
    private Integer incDEX;
    private Integer incINT;
    private Integer incLUK;
    private Integer incPAD;
    private Integer incMAD;
    private Integer incPDD;
    private Integer incMDD;
    private Integer incMHP;
    private Integer incMMP;
    private Integer incEVA;
    private Integer incACC;
    private Integer incJump;
    private Integer incSpeed;


    public MapleEquip(Integer id, Integer position) {
        super(id, position);
        this.setType(MapleInventoryType.EQUIP);
    }

    @Override
    public IMapleItem copy() {
        MapleEquip ret = new MapleEquip(getItemId(), this.getPosition());
        ret.incSTR = incSTR;
        ret.incDEX = incDEX;
        ret.incINT = incINT;
        ret.incLUK = incLUK;
        ret.incMHP = incMHP;
        ret.incMMP = incMMP;
        ret.incEVA = incEVA;
        ret.incACC = incACC;
        ret.incJump = incJump;
        ret.incSpeed = incSpeed;
        ret.incPAD = incPAD;
        ret.incMAD = incMAD;
        ret.incPDD = incPDD;
        ret.incMDD = incMDD;
        return ret;
    }
}
