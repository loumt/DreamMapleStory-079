package cn.ms.dm.server.client;

import cn.ms.dm.core.enums.Gender;
import cn.ms.dm.maple.constant.account.VipLevel;
import cn.ms.dm.maple.netty.PacketCrypto;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LouMT
 * @name MapleClient
 * @date 2026-02-10 16:39
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@Setter
public class MapleClient {
    public static final AttributeKey<MapleClient> CLIENT_KEY = AttributeKey.valueOf("Client");
    //封包加密工具
    private final PacketCrypto sendCrypto, receiveCrypto;
    private final Channel session;

    //登录信息
    private int channelNo;
    private int world;

    //可以登陆的角色ID
    private final transient List<Long> characterIds = Lists.newArrayList();

    //尝试登录次数
    private transient short loginAttempt = 0;

    //用户信息
    private Integer accountId;
    private VipLevel accountVip;
    private String accountName;
    private Gender gender;
    private MapleCharacter player;
    //心跳时间戳
    private transient long lastPong = 0, lastPing = 0;

    private final transient Lock mutex = new ReentrantLock(true);

    public MapleClient(PacketCrypto send, PacketCrypto receive, Channel session) {
        this.sendCrypto = send;
        this.receiveCrypto = receive;
        this.session = session;
    }

    /**
     * 发送数据包
     */
    public void sendPacket(byte[] packet) {
        this.getSession().writeAndFlush(packet);
    }

    /**
     * 判断是否可以再次尝试登录
     */
    public boolean isAttemptLogin() {
        return ++loginAttempt < AppConfigProperties.LOGIN_ATTEMPT_COUNT;
    }

    /**
     * ping -> pong
     */
    public void pong() {
        this.lastPong = System.currentTimeMillis();
    }


    /**
     * 发送系统消息
     */
    public void sendMessage(String content){
        sendPacket(MessagePacketCreator.serverNotice(1, content));
    }
}
