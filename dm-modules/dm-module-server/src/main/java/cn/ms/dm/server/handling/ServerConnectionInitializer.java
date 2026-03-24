package cn.ms.dm.server.handling;

import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author LouMT
 * @name ServerConnectionInitializer
 * @date 2026-02-06 16:43
 * @email lmtemail163@163.com
 * @description
 */
@Slf4j
@Setter
@Component
@Scope("prototype")
public class ServerConnectionInitializer extends ChannelInitializer<SocketChannel> {
    private ChannelTypeEnum channelType;
    private int channelNo;
    @Resource
    private MapleServerHandler handler;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipe = channel.pipeline();
        pipe.addLast(new IdleStateHandler(90, 90, 0));
        pipe.addLast("decoder", new MaplePacketDecoder()); // decodes the packet
        pipe.addLast("encoder", new MaplePacketEncoder()); //encodes the packet

        handler.setChannelNo(channelNo);
        handler.setChannelType(channelType);
        pipe.addLast("handler", handler);
    }
}
