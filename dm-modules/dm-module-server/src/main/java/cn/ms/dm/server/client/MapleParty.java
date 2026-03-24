package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author LouMT
 * @name MapleParty
 * @date 2026-02-28 15:02
 * @email lmtemail163@163.com
 * @description 队伍
 */
@Getter
@Setter
public class MapleParty  implements Serializable {
    @Serial
    private static final long serialVersionUID = -4794805064744500585L;
    //队伍ID
    private Integer id;
    //组长
    private MaplePartyCharacter leader;
    //队员
    private final List<MaplePartyCharacter> members = new LinkedList<>();

    public MapleParty(Integer id, MaplePartyCharacter leader) {
        this.id = id;
        this.leader = leader;
    }
}
