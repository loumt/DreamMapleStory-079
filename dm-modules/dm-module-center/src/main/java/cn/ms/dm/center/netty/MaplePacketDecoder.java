package cn.ms.dm.center.netty;

import cn.ms.dm.center.client.MapleClient;
import cn.ms.dm.center.netty.constant.NettyAttributeKey;
import cn.ms.dm.core.enums.YesOrNo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author LouMT
 * @name MaplePacketDecoder
 * @date 2025-08-11 13:39
 * @email lmtemail163@163.com
 * @description
 */
public class MaplePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> message) throws Exception {
        final MapleClient client = chc.channel().attr(NettyAttributeKey.CLIENT_KEY).get();
        final YesOrNo decoderState =  chc.channel().attr(NettyAttributeKey.DECODER_STATE_KEY).get();
    }
}
