package cn.ms.dm.server.client;

import cn.ms.dm.maple.constant.alliance.AllianceRankType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleAllianceGuild
 * @date 2026-03-25 11:21
 * @email lmtemail163@163.com
 * @description 联盟-公会
 */
@Getter
@Setter
public class MapleAllianceGuild implements Serializable {
    @Serial
    private static final long serialVersionUID = -4700954323773661763L;
    //公会信息
    private Integer id;
    private String name;

}
