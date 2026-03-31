package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LouMT
 * @name MapleBuddyStorage
 * @date 2026-03-30 9:54
 * @email lmtemail163@163.com
 * @description 好友数据缓存
 */
@Setter
@Getter
public class MapleBuddyStorage implements Serializable {
    @Serial
    private static final long serialVersionUID = 7671892427305156512L;

    /**
     * 好友集合
     */
    private final Map<Integer, MapleBuddy> buddies;

    /**
     * 容量
     */
    private int capacity;

    public MapleBuddyStorage(int capacity) {
        this.buddies = new LinkedHashMap<>();
        this.capacity = capacity;
    }

    /**
     * 是否有这个好友
     *
     * @param characterId
     */
    public boolean contains(int characterId) {
        return buddies.containsKey(characterId);
    }

    /**
     * 确认是否有这个好友且在线
     *
     * @param charId
     */

    public boolean containsVisible(int charId) {
        MapleBuddy ble = buddies.get(charId);
        if (ble == null) {
            return false;
        }
        return ble.isVisible();
    }

    /**
     * 由好友名取得好友
     *
     * @param characterName
     */
    public MapleBuddy get(String characterName) {
        String searchName = characterName.toLowerCase();
        for (MapleBuddy ble : buddies.values()) {
            if (ble.getName().toLowerCase().equals(searchName)) {
                return ble;
            }
        }
        return null;
    }

    /**
     * 新增好友
     *
     * @param newEntry 新增的好友
     */
    public void put(MapleBuddy newEntry) {
        buddies.put(newEntry.getPlayerId(), newEntry);
    }

    /**
     * 由角色ID從清單中刪除好友
     *
     * @param characterId 角色ID
     */
    public void remove(int characterId) {
        buddies.remove(characterId);
    }

    /**
     * 回傳好友清單
     *
     * @return 好友清單集合
     */
    public Collection<MapleBuddy> getBuddies() {
        return buddies.values();
    }

    /**
     * 取得好友名單是否滿
     *
     * @return 好友名單是否已經滿了
     */
    public boolean isFull() {
        return buddies.size() >= capacity;
    }

    /**
     * 取得所有好友的ID
     *
     * @return 好友清單的ID集合
     */
    public Collection<Integer> getBuddiesIds() {
        return buddies.keySet();
    }
}
