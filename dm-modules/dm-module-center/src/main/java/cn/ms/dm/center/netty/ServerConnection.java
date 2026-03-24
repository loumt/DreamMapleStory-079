package cn.ms.dm.center.netty;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.constant.netty.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


/**
 * @author LouMT
 * @name ServerConnection
 * @date 2025-08-11 14:12
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@Slf4j(topic = "ServerConnection")
@AllArgsConstructor
public class ServerConnection {
    private Integer port;
    private Integer world;
    private Integer channels;
    private ServerBootstrap boot;
    private Channel channel;

    //bossGroup用于服务端接收客户端的连接
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();

    //workerGroup用于网络读写
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public ServerConnection(Integer port) {
        this.port = port;
        this.world = -1;
        this.channels = -1;
    }

    public void run() {
        try {
            this.boot = new ServerBootstrap();
            this.boot.group(bossGroup, workerGroup);
            this.boot.channel(NioServerSocketChannel.class);
            this.boot.option(ChannelOption.SO_BACKLOG, NettyConstant.USER_LIMIT);
            this.boot.childOption(ChannelOption.TCP_NODELAY, true);
            this.boot.childOption(ChannelOption.SO_KEEPALIVE, true);
            this.boot.childHandler(new MapleNettyInitializer(world, channels));

            this.channel = this.boot.bind(this.port).sync().channel().closeFuture().channel();
        } catch (Exception e) {
            log.info(e.getMessage());
            close();
        }
    }

    private void close() {
        if (ObjectUtil.isNotNull(channel)) channel.close();
        //优雅退出，释放线程池资源
        bossGroup.shutdownGracefully();
        //优雅退出，释放线程池资源
        workerGroup.shutdownGracefully();
    }
}
