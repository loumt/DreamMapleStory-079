package cn.ms.dm.server.handling.channel;

import cn.ms.dm.core.utils.SpringUtils;
import cn.ms.dm.maple.base.BaseMapleServer;
import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import cn.ms.dm.maple.constant.netty.ServerStatus;
import cn.ms.dm.server.client.MapleCharacter;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.handling.ServerConnection;
import cn.ms.dm.server.handling.login.LoginServer;
import cn.ms.dm.server.operation.WorldOperation;
import cn.ms.dm.server.operation.packet.creator.MessagePacketCreator;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serial;

/**
 * @author LouMT
 * @name ChannelServer
 * @date 2026-02-09 16:44
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j(topic = "【频道服务】")
@Setter
@Getter
@Component
@Scope("prototype")
public class ChannelServer extends BaseMapleServer {
    @Serial
    private static final long serialVersionUID = -8658887773883501256L;
    @Resource
    private ServerConnection acceptor;
    //频道号, 端口号
    private String host;
    private int channelNo, port;
    //玩家缓存
    private CharacterStorage storage;
    @Resource
    private LoginServer loginServer;

    /**
     * 频道初始化
     */
    public void init(){
        host = AppConfigProperties.MAPLE_IP;
        port = AppConfigProperties.CHANNEL_DEFAULT_PORT + channelNo;
        acceptor.init(port,  ChannelTypeEnum.CHANNEL_SERVER, channelNo);
        log.info("【频道服务】 频道:【{}】 - 监听端口: {}", channelNo, port);
        //初始化角色缓存容器
        storage = new CharacterStorage(channelNo);
        //绑定
        loginServer.addChannel(channelNo);
        ChannelServerGroup.addChannelServer(channelNo, this);
    }

    /**
     * 关闭频道
     */
    public void shutdown(){
        if(isShutDown()) return;
        acceptor.close();
        status = ServerStatus.SHUTDOWN;
        log.info("【频道服务】 - 已关闭");
    }

    public static void startAllChannels() {
        int maxChannelCount = Math.max(AppConfigProperties.CHANNEL_COUNT, 1);
        maxChannelCount = Math.min(maxChannelCount, 20);

        log.info("【频道数】 {}", maxChannelCount);
        for(int i = 0; i < maxChannelCount; i ++){
            ChannelServer server = SpringUtils.getBean(ChannelServer.class);
            //设置频道号
            server.setChannelNo(ChannelTypeEnum.CHANNEL_SERVER.getChannelNo() + i);
            server.startup();
        }
    }

    /**
     * 玩家加入频道
     */
    public final  void enterCharacter(final MapleCharacter character){
        log.info("频道号:{}  【玩家: {}】加入", channelNo, character.getName());
        storage.register(character);
        //注册到查询器
        WorldOperation.Find.register(character.getPlayerId(), channelNo);
        //消息发送? 需要看一下这个消息是什么消息
        character.getClient().sendPacket(MessagePacketCreator.serverMessage("这是一条测试消息"));
    }

    /**
     * 玩家离开频道
     */
    public final  void leaveCharacter(final MapleCharacter character){
        log.info("频道号:{}  【玩家: {}】离开", channelNo, character.getName());
        storage.deregister(character);
        //注销对应数据
        WorldOperation.Find.deregister(character.getPlayerId());
    }

    /**
     * 获取玩家数量
     */
    public Integer getCharacterSize() {
        return storage.getCharacterSize();
    }

    /**
     * 获取当前频道内的玩家-Method1
     */
    public MapleCharacter getCharacter(final Integer characterId){
        return storage.getCharacterById(characterId);
    }

    /**
     * 获取当前频道内的玩家-Method2
     */
    public MapleCharacter getCharacter(final String characterName){
        return storage.getCharacterByName(characterName);
    }
}
