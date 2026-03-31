package cn.ms.dm.server.client;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name MapleBuddy
 * @date 2026-03-30 9:35
 * @email lmtemail163@163.com
 * @description 好友数据
 */
@Getter
@Setter
public class MapleBuddy implements Serializable {
    @Serial
    private static final long serialVersionUID = -5416045625829702162L;

    /**
     * 好友
     */
    private final String name;
    private final Integer playerId, level, job;

    /**
     * 好友可見度
     */
    private boolean visible;

    /**
     * 好友所在群組
     */
    private String group;

    /**
     * 好友頻道
     */
    private int channelNo;

    public MapleBuddy(String group, Integer playerId, String name, Integer level, Integer job, Integer channelNo, boolean visible) {
        super();
        this.group = group;
        this.playerId = playerId;
        this.name = name;
        this.level = level;
        this.job = job;
        this.channelNo = channelNo;
        this.visible = visible;
    }
}
