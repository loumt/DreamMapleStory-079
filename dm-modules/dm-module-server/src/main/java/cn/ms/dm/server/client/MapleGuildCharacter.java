package cn.ms.dm.server.client;

import cn.ms.dm.maple.constant.guild.GuildRankType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleGuildCharacter
 * @date 2026-03-03 15:25
 * @email lmtemail163@163.com
 * @description 家族成员信息类
 */
@Getter
@Setter
public class MapleGuildCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = 8200559762114996952L;

    //角色信息
    private String name;
    private Integer id,level,job;
    private GuildRankType rank;
    private boolean online = false;


}
