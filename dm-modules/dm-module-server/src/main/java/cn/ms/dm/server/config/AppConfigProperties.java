package cn.ms.dm.server.config;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name AppConfigProperties
 * @date 2026-02-02 9:47
 * @email lmtemail163@163.com
 * @description
 */
@Component
@Getter
@Setter
public class AppConfigProperties {
    /**
     * 服务名
     */
    @Value("${maple.name: 'MapleStory079'}")
    private String name;
    /**
     * 版本号
     */
    @Value("${maple.version}")
    private Short version;
    /**
     * 服务消息
     */
    @Value("${maple.message}")
    private String message;
    /**
     * 开启管理员模式
     */
    @Value("${maple.admin: false}")
    private Boolean admin;
    /**
     * IP地址
     */
    @Value("${maple.ip: 127.0.0.1}")
    private String ip;
    /**
     * 登录服务器端口
     */
    @Value("${maple.login.port: 8484}")
    private Integer loginServerPort;
    /**
     * 登录服务器端口
     */
    @Value("${maple.cash-shop.port:5555}")
    private Integer cashShopServerPort;

    /**
     * 频道起始端口
     */
    @Value("${maple.channel.default-port:17575}")
    private Integer channelDefaultPort;

    /**
     * 频道数
     */
    @Value("${maple.channel.count:1}")
    private Integer channelCount;

    /**
     * 可以尝试登录的最大次数
     */
    @Value("${maple.login.login-attempt:5}")
    private Integer loginAttemptCount;

    /**
     * 创建家族所需要的金币数
     */
    @Value("${maple.guild.cost:500000}")
    private Integer guildCost;

    public static Integer LOGIN_SERVER_PORT;
    public static Integer LOGIN_ATTEMPT_COUNT;
    public static Short MAPLE_VERSION;
    public static String MAPLE_IP;
    public static Integer CASH_SHOP_SERVER_PORT;
    public static Integer CHANNEL_DEFAULT_PORT;
    public static Integer CHANNEL_COUNT;
    public static Integer GUILD_CREATE_COST;

    @PostConstruct
    public void init(){
        LOGIN_SERVER_PORT = loginServerPort;
        CASH_SHOP_SERVER_PORT = cashShopServerPort;
        CHANNEL_COUNT = channelCount;
        CHANNEL_DEFAULT_PORT = channelDefaultPort;
        MAPLE_VERSION = version;
        MAPLE_IP = ip;
        LOGIN_ATTEMPT_COUNT = loginAttemptCount;
        GUILD_CREATE_COST = guildCost;
    }
}
