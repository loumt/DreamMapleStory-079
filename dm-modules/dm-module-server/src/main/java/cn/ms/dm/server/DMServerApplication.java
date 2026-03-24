package cn.ms.dm.server;

import cn.ms.dm.core.domain.Pair;
import cn.ms.dm.maple.base.IMapleItem;
import cn.ms.dm.maple.constant.inventory.MapleInventoryType;
import cn.ms.dm.maple.wz.base.MapleData;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.database.service.AccountService;
import cn.ms.dm.server.handling.cashshop.CashShopServer;
import cn.ms.dm.server.handling.channel.ChannelServer;
import cn.ms.dm.server.handling.login.LoginServer;
import cn.ms.dm.server.operation.CacheOperation;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.WzOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

/**
 * @author LouMT
 * @name DMServerApplication
 * @date 2025-08-29 15:22
 * @email lmtemail163@163.com
 * @description
 */
@AllArgsConstructor
@Slf4j(topic = "【服务启动】")
@SpringBootApplication
@EnableScheduling
public class DMServerApplication {
    private final AppConfigProperties appConfigProperties;
    private final AccountService accountService;
    private final CashShopServer cashShopServer;
    private final LoginServer loginServer;

    /**
     * 总服务启动
     */
    public static void main(String[] args) {
        runSpringApplication(args);
        System.out.println("【***【***【***【***【***【***【启动成功】***】***】***】***】***】***】");
    }

    private static void runSpringApplication(String[] args) {
        SpringApplication application = new SpringApplication(DMServerApplication.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    public void runMapleStoryWorld(){
        System.setProperty("file.encoding", "utf-8");
        System.setProperty("path", "");
        log.info("【冒险岛模拟器】");
        log.info("【版本】 v079");
        log.info("【管理員模式】 {}", appConfigProperties.getAdmin()? "开启": "关闭");
        //重置账号状态
        accountService.resetAccountLoginStatus();
        //加载物品数据
        WzOperation.init();
        //加载世界数据
        WorldOperation.init();

        //启动登录服务器
        loginServer.startup();
        //启动商城
        cashShopServer.startup();
        //启动频道服务器
        ChannelServer.startAllChannels();

        //加载缓存数据
        CacheOperation.init();
    }

}