package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleGuildAlliance
 * @date 2026-03-03 15:49
 * @email lmtemail163@163.com
 * @description 联盟信息
 */
@Getter
@Setter
public class MapleGuildAlliance implements Serializable {
    @Serial
    private static final long serialVersionUID = 38658444427542113L;
    //联盟信息
    private Integer id;
    private String name;
    private String notice;

}
