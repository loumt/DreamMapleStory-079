package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleMessengerCharacter
 * @date 2026-03-03 16:20
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@Setter
public class MapleMessengerCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = -4571019262092675434L;

    private Integer id;
    private String name;
}
