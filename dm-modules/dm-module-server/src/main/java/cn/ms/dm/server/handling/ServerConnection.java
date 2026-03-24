package cn.ms.dm.server.handling;

import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import cn.ms.dm.maple.constant.netty.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name ServerConnection
 * @date 2026-02-06 16:38
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j
@Setter
@Getter
@Component
@Scope("prototype")
public class ServerConnection {
    /**
     * 端口
     */
    private int port;
    /**
     * 通道类型
     */
    private ChannelTypeEnum channelType;
    /**
     * 频道号，登录服务0 商城-10 频道服务从1开始递增
     */
    private int channelNo;

    private ServerBootstrap bootstrap;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private Channel channel;

    @Resource
    private ServerConnectionInitializer initializer;

    public void init(int port, ChannelTypeEnum channelType) {
        init(port, channelType, channelType.getChannelNo());
    }

    public void init(int port, ChannelTypeEnum channelType, int channelNo) {
        this.port = port;
        this.channelNo = channelNo;
        this.channelType = channelType;
        this.run();
    }

    public void run() {
        initializer.setChannelNo(channelType.getChannelNo());
        initializer.setChannelType(channelType);

        try {
            bootstrap = new ServerBootstrap().group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, NettyConstant.USER_LIMIT)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(initializer);
            channel = bootstrap.bind(port).sync().channel().closeFuture().channel();
        } catch (Exception e) {
            log.error("Connection to %s failed. {}", channel == null ? e.toString() : channel.remoteAddress());
            //关闭服务
            close();
        }
    }


    public void close() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
