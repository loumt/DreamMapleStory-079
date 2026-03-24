package cn.ms.dm.server.handling.login;

import cn.ms.dm.maple.base.BaseMapleServer;
import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import cn.ms.dm.maple.constant.netty.ServerStatus;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.handling.ServerConnection;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LouMT
 * @name LoginServer
 * @date 2026-02-09 16:36
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【登录服务】")
@Getter
@Component
public class LoginServer extends BaseMapleServer {
    @Serial
    private static final long serialVersionUID = 4070449730929184656L;
    private static int userOnline = 0;

    @Resource
    private ServerConnection acceptor;
    //缓存
    private AccountStorage storage;

    //这个是干嘛的
    private static final Map<Integer, Integer> load = new HashMap<>();

    public void init(){
        acceptor.init(AppConfigProperties.LOGIN_SERVER_PORT , ChannelTypeEnum.LOGIN_SERVER);
        log.info("【启动中】 登录服务器::: 监听端口: {}", AppConfigProperties.LOGIN_SERVER_PORT);

        //初始化缓存容器
        storage = new AccountStorage();
    }

    public void shutdown(){
        if(isShutDown()) return;
        acceptor.close();
        status = ServerStatus.SHUTDOWN;
        log.info("【已关闭】 登录服务器:::");
    }

    public final void addChannel(final int channel) {
        load.put(channel, 0);
    }

    public final void removeChannel(final int channel) {
        load.remove(channel);
    }

}
