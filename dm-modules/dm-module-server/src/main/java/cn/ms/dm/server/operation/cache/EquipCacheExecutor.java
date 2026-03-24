package cn.ms.dm.server.operation.cache;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.wz.MapleDataTool;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.server.client.MapleEquip;
import cn.ms.dm.server.operation.WzOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LouMT
 * @name EquipCacheExecutor
 * @date 2026-03-18 15:54
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【装备缓存】")
@Component
public class EquipCacheExecutor {
    //装备信息缓存
    private static final Map<Integer, MapleEquip> EQUIP_CACHE = new HashMap<>();

    public IMapleItem getByItemId(final Integer itemId) {
        if (EQUIP_CACHE.containsKey(itemId)) return EQUIP_CACHE.get(itemId);

        //新建物品-装备对象
        final MapleEquip equip = new MapleEquip(itemId, 0);

        final String xmlItemId = "0" + itemId;
        MapleData mapleData = WzOperation.Character.getMapleData(xmlItemId);
        if (ObjectUtil.isNull(mapleData)) return equip;
        if (ObjectUtil.isNull(mapleData.getChildByPath("info"))) return equip;

        MapleData info = mapleData.getChildByPath("info");

        equip.setReqLevel(MapleDataTool.getInt("reqLevel", info, 0));
        equip.setReqJob(MapleDataTool.getInt("reqJob", info, 0));
        equip.setReqSTR(MapleDataTool.getInt("reqSTR", info, 0));
        equip.setReqDEX(MapleDataTool.getInt("reqDEX", info, 0));
        equip.setReqINT(MapleDataTool.getInt("reqINT", info, 0));
        equip.setReqLUK(MapleDataTool.getInt("reqLUK", info, 0));

        equip.setIncSTR(MapleDataTool.getInt("incSTR", info, 0));
        equip.setIncDEX(MapleDataTool.getInt("incDEX", info, 0));
        equip.setIncINT(MapleDataTool.getInt("incINT", info, 0));
        equip.setIncLUK(MapleDataTool.getInt("incLUK", info, 0));

        equip.setIncPAD(MapleDataTool.getInt("incPAD", info, 0));
        equip.setIncMAD(MapleDataTool.getInt("incMAD", info, 0));
        equip.setIncPDD(MapleDataTool.getInt("incPDD", info, 0));
        equip.setIncMDD(MapleDataTool.getInt("incMDD", info, 0));
        equip.setIncMHP(MapleDataTool.getInt("incMHP", info, 0));
        equip.setIncMMP(MapleDataTool.getInt("incMMP", info, 0));

        equip.setIncACC(MapleDataTool.getInt("incACC", info, 0));
        equip.setIncEVA(MapleDataTool.getInt("incEVA", info, 0));

        equip.setIncSpeed(MapleDataTool.getInt("incSpeed", info, 0));
        equip.setIncJump(MapleDataTool.getInt("incMMP", info, 0));
        //升级次数
        equip.setTuc(MapleDataTool.getInt("tuc", info, 0));
        EQUIP_CACHE.put(itemId, equip);
        return equip.copy();
    }

}
