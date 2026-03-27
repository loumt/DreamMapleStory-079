package cn.ms.dm.center.netty;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.center.client.MapleClient;
import cn.ms.dm.center.netty.constant.NettyAttributeKey;
import cn.ms.dm.maple.constant.packet.SendPacketOpcode;
import cn.ms.dm.maple.netty.PacketCrypto;
import cn.ms.dm.maple.utils.HexUtil;
import cn.ms.dm.maple.utils.MapleCustomEncryption;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;

/**
 * @author LouMT
 * @name MaplePacketEncoder
 * @date 2025-08-11 13:39
 * @email lmtemail163@163.com
 * @description 数据发送数据包处理器
 */
@Slf4j
public class MaplePacketEncoder extends MessageToByteEncoder<Object> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf buffer) throws Exception {
        final MapleClient client = ctx.channel().attr(NettyAttributeKey.CLIENT_KEY).get();

        if(ObjectUtil.isNotNull(client)){
            final PacketCrypto crypto = client.getSendCrypto();

            byte[] input = ((byte[]) message);
            int pHeader = ((input[0]) & 0xFF) + (((input[1]) & 0xFF) << 8);
            //指令名
            String opType = SendPacketOpcode.nameOf(pHeader);
            //日志输出
            logSendOpMessage(opType, input);

            final byte[] unencrypted = new byte[input.length];
            System.arraycopy(input, 0, unencrypted, 0, input.length);
            final byte[] ret = new byte[unencrypted.length + 4];

            //加锁
            Lock packetLock = client.getPacketLock();
            packetLock.lock();
            try {
                //数据加密
                final byte[] header = crypto.getPacketHeader(unencrypted.length);
                MapleCustomEncryption.encryptData(unencrypted);
                crypto.crypt(unencrypted);
                System.arraycopy(header, 0, ret, 0, 4);
                System.arraycopy(unencrypted, 0, ret, 4, unencrypted.length);
                buffer.writeBytes(ret);
            }finally {
                packetLock.unlock();
            }
        }else{
            byte[] input = (byte[]) message;
            buffer.writeBytes(input);
        }
    }

    /**
     * 打印发送数据包信息
     */
    private void logSendOpMessage(String opType, byte[] input) {
        log.info("[发送] op_type:{} size:{} content:{} content2:{}", opType, input.length, HexUtil.toString(input), HexUtil.toStringFromAscii(input));
    }
}
