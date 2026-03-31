package cn.ms.dm.domain.buddy.vo;

import cn.ms.dm.domain.buddy.Buddy;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author LouMT
 * @name BuddyDetailVO
 * @date 2026-03-30 10:20
 * @email lmtemail163@163.com
 * @description
 */
@Setter
@Getter
public class BuddyVO extends Buddy {
    @Serial
    private static final long serialVersionUID = 6010219206137489083L;

    private String name;

    private Integer level;

    private Integer job;

}
