package cn.ms.dm.server.client;

import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleAllianceRank
 * @date 2026-03-25 11:22
 * @email lmtemail163@163.com
 * @description 联盟-玩家成员
 */
@Getter
@Setter
public class MapleAllianceCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 9064138739123769333L;
    //角色信息
    private Integer characterId;
    private String name;

    //职级
    private AllianceRankType rank;
}
