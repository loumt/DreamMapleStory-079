package cn.ms.dm.server.client;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author LouMT
 * @name MapleGuild
 * @date 2026-03-03 15:22
 * @email lmtemail163@163.com
 * @description 公会
 */
@Setter
@Getter
public class MapleGuild implements Serializable {
    @Serial
    private static final long serialVersionUID = 3609202007294275787L;
    //公会信息
    private Integer id;
    private String name;
    private String notice;
    //联盟信息
    private Integer allianceId;
    //公会成员
    private final List<MapleGuildCharacter> members = Lists.newCopyOnWriteArrayList();

    public void addMember(MapleGuildCharacter member){
        this.members.add(member);
    }

}
