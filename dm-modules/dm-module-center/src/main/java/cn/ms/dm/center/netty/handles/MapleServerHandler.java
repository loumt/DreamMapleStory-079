package cn.ms.dm.center.netty.handles;

import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author LouMT
 * @name MapleServerHandler
 * @date 2025-08-11 14:45
 * @email lmtemail163@163.com
 * @description
 */
@Getter
@AllArgsConstructor
public class MapleServerHandler extends ChannelInboundHandlerAdapter {
    private Integer world;
    private Integer channel;
}
