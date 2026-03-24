package cn.ms.dm.center.netty;


import cn.ms.dm.center.netty.handles.MapleServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author LouMT
 * @name MapleNettyInitializer
 * @date 2025-08-11 13:37
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@AllArgsConstructor
public class MapleNettyInitializer extends ChannelInitializer<SocketChannel> {
    private Integer world;
    private Integer channels;

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new IdleStateHandler(90, 90, 0));

        //decodes the packets
        pipeline.addLast(new MaplePacketDecoder());

        //encodes the packets
        pipeline.addLast(new MaplePacketEncoder());

        //handle
        pipeline.addLast(new MapleServerHandler(world, channels));
    }
}
