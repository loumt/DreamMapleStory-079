package cn.ms.dm.maple.base;


import cn.ms.dm.maple.constant.netty.ServerStatus;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author LouMT
 * @name BaseMapleServer
 * @date 2026-02-10 16:22
 * @email lmtemail163@163.com
 * @description
 */
public abstract class BaseMapleServer implements Serializable {
    @Serial
    private static final long serialVersionUID = -1L;
    /**
     * 服务启动时间
     */
    protected Long startTimes;
    /**
     * 改通道状态
     */
    protected ServerStatus status;

    /**
     * 启动服务
     */
    public void startup(){
        init();
        startTimes = System.currentTimeMillis();
        status = ServerStatus.DURING;
    }

    /**
     * 初始化操作
     */
    protected abstract void init();

    public Boolean isShutDown(){
        return status == ServerStatus.SHUTDOWN;
    }

}
