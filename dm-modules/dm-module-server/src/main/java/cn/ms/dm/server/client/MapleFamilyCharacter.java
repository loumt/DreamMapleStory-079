package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleFamilyCharacter
 * @date 2026-03-04 14:43
 * @email lmtemail163@163.com
 * @description 家族成员
 */
@Getter
@Setter
public class MapleFamilyCharacter implements Serializable {
    @Serial
    private static final long serialVersionUID = -88989449697055944L;
    //角色信息
    private String name;
    private Integer id,level;
}
