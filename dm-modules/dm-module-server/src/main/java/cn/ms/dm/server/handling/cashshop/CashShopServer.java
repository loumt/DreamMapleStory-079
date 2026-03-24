package cn.ms.dm.server.handling.cashshop;

import cn.ms.dm.maple.base.BaseMapleServer;
import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import cn.ms.dm.maple.constant.netty.ServerStatus;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.handling.ServerConnection;
import cn.ms.dm.server.handling.channel.CharacterStorage;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.Serial;

/**
 * @author LouMT
 * @name CashShopServer
 * @date 2026-02-06 16:28
 * @email lmtemail163@163.com
 * @description 商城服务
 */
@Slf4j(topic = "【商城服务】")
@Getter
@Component
public class CashShopServer extends BaseMapleServer {
    @Serial
    private static final long serialVersionUID = -6314641453945334722L;
    @Resource
    private ServerConnection acceptor;
    //玩家缓存
    private CharacterStorage storage;

    public void init(){
        acceptor.init(AppConfigProperties.CASH_SHOP_SERVER_PORT,  ChannelTypeEnum.CASH_SHOP_SERVER);
        log.info("【已启动】 购物商城::: 监听端口: {}", AppConfigProperties.CASH_SHOP_SERVER_PORT);

        //初始化角色缓存容器
        storage = new CharacterStorage(ChannelTypeEnum.CASH_SHOP_SERVER.getChannelNo());
    }

    public void shutdown(){
        if(isShutDown()) return;
        acceptor.close();
        status = ServerStatus.SHUTDOWN;
        log.info("【已关闭】 购物商城:::");
    }

}
