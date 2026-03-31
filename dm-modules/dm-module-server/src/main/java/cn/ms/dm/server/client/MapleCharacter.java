package cn.ms.dm.server.client;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.maple.base.AbstractAnimatedMapleMapObject;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.account.CharacterStat;
import cn.ms.dm.maple.constant.guild.GuildRankType;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.maple.constant.map.MapleMapObjectType;
import cn.ms.dm.server.operation.packet.creator.CharacterPacketCreator;
import cn.ms.dm.server.operation.packet.creator.MaplePacketCreator;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

/**
 * @author LouMT
 * @name MapleCharacter
 * @date 2026-02-13 13:24
 * @email lmtemail163@163.com
 * @description 角色
 */
@Getter
@Setter
@NoArgsConstructor
public class MapleCharacter extends AbstractAnimatedMapleMapObject implements Serializable {
    @Serial
    private static final long serialVersionUID = 5118829108558755037L;
    //客户端
    private MapleClient client;
    //当前地图
    private transient MapleMap map;
    //是否可以聊天
    private boolean canTalk = true;
    //角色信息
    private Long exp, meso;
    private Integer playerId, world, level, job, hair, face, skinColor, mapId;
    private String name;
    private Short fame, remainingAp;
    private Integer[] remainingSp;
    private Gender gender;
    //出生点
    private Byte spawnPoint;
    //角色状态
    private MaplePlayerStatus status;
    //队伍
    private MapleParty party;
    //公会信息
    private Integer guildId;
    private String guildName;
    //物品清单
    private MapleInventory[] inventory;
    //公会
    private MapleGuildCharacter mgc;
    //聊天室
    private MapleMessenger messenger;

    @Override
    public MapleMapObjectType getType() {
        return MapleMapObjectType.PLAYER;
    }



    /************************获取技能点********************************/
    public Integer getRemainingSp(){
        return remainingSp[0];
    }


    /************************获取物品清单********************************/

    public final MapleInventory getInventory(MapleInventoryType type) {
        return inventory[type.ordinal()];
    }

    public final Collection<IMapleItem> getInventoryByType(MapleInventoryType type) {
        MapleInventory ivt = inventory[type.ordinal()];
        return ivt.list();
    }

    /**
     * 设置物品类型数量插槽
     */
    public void setInventorySlot(MapleInventoryType type, Integer slot) {
        if(ObjectUtil.isNull(inventory)){
            initInventory();
        }
        inventory[type.ordinal()].setSlot(slot);
    }

    /**
     * 初始化物品类型列表
     */
    public void initInventory() {
        MapleInventory[] inventories = new MapleInventory[MapleInventoryType.values().length];
        for (MapleInventoryType value : MapleInventoryType.values()) {
            inventories[value.ordinal()] = new MapleInventory(value);
        }
        inventory = inventories;
    }

    /**
     * 发送数据包
     */
    public void sendPacket(final byte[] packet){
        if(client != null) client.sendPacket(packet);
    }

    /**
     * 金币变动
     */
    public void gainMeso(Integer cost) {
        if (meso + cost < 0){
            sendPacket(CharacterPacketCreator.enableActions());
            return;
        }
        setMeso(meso + cost);
        sendPacket(CharacterPacketCreator.updateStats(CharacterStat.MESO, meso.intValue(), false));
    }

    /**
     * 发送系统消息
     */
    public void sendMessage(String content){
        sendPacket(MessagePacketCreator.serverNotice(1, content));
    }
}
