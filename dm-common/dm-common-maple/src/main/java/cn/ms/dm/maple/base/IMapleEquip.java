package cn.ms.dm.maple.base;

/**
 * @description 武器
 * @author LouMT
 * @date 2026/3/20 13:35
 */
public interface IMapleEquip {
    /*************************************需求**************************************/
    //等级
    Integer getReqLevel();
    //职业
    Integer getReqJob();
    //力量
    Integer getReqSTR();
    //敏捷
    Integer getReqDEX();
    //智力
    Integer getReqINT();
    //运气
    Integer getReqLUK();
    /*************************************属性增加**************************************/
    //物理攻击力
    Integer getIncPAD();
    //魔法攻击力
    Integer getIncMAD();
    //物理防御
    Integer getIncPDD();
    //魔法防御
    Integer getIncMDD();
    //最大血量
    Integer getIncMHP();
    //最大魔量
    Integer getIncMMP();
    //移动速度
    Integer getIncSpeed();
    //跳跃力
    Integer getIncJump();
    //回避率
    Integer getIncEVA();
    /*************************************其他**************************************/
    //升级次数
    Integer getTuc();
    /*************************************武器**************************************/
    //攻击速度
    Integer getAttackSpeed();
    //击退怪物几率
    Integer getKnockback();
}
