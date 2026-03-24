package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LouMT
 * @name MapleFamily
 * @date 2026-03-04 14:42
 * @email lmtemail163@163.com
 * @description 家族
 */
@Getter
@Setter
public class MapleFamily implements Serializable {
    @Serial
    private static final long serialVersionUID = -57224606716152558L;
    //家族信息
    private String name, notice;
    private Integer id, capacity;
    //家族成员
    private final Map<Long, MapleFamilyCharacter> members = new ConcurrentHashMap<>();

    public void addMember(Long characterId, MapleFamilyCharacter mfc) {
        this.members.put(characterId, mfc);
    }
}
