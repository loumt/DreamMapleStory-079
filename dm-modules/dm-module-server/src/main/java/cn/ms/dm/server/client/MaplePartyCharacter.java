package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MaplePartyCharacter
 * @date 2026-02-28 15:03
 * @email lmtemail163@163.com
 * @description 队伍角色
 */
@Getter
@Setter
public class MaplePartyCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = -4046575139566290262L;

    //角色Id
    private String name;
    private Integer id, level, jobId, channel, mapId;
    private boolean online;
}
