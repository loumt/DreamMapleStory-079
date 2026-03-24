package cn.ms.dm.server.handling;

import cn.hutool.core.util.ObjectUtil;
import cn.ms.dm.maple.constant.netty.ChannelTypeEnum;
import cn.ms.dm.maple.constant.ReceivePacketOpcode;
import cn.ms.dm.maple.netty.PacketCrypto;
import cn.ms.dm.maple.netty.ByteArrayByteStream;
import cn.ms.dm.maple.netty.LittleEndianAccessor;
import cn.ms.dm.maple.utils.Randomizer;
import cn.ms.dm.server.client.MapleClient;
import cn.ms.dm.server.config.AppConfigProperties;
import cn.ms.dm.server.database.service.PacketLogService;
import cn.ms.dm.server.operation.PacketOperation;
import cn.ms.dm.server.handling.cashshop.CashShopServer;
import cn.ms.dm.server.handling.channel.ChannelServerGroup;
import cn.ms.dm.server.handling.login.LoginServer;
import cn.ms.dm.server.operation.packet.creator.LoginPacketCreator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

/**
 * @author LouMT
 * @name MapleServerHandler
 * @date 2026-02-10 15:30
 * @email lmtemail163@163.com
 * @description 服务端指令核心处理器
 */
@Slf4j(topic = "[处理客户端指令]")
@Setter
@Component
@ChannelHandler.Sharable
public class MapleServerHandler extends ChannelInboundHandlerAdapter {
    private int channelNo;
    private ChannelTypeEnum channelType;
    @Resource
    @Lazy
    private LoginServer loginServer;
    @Resource
    private CashShopServer cashShopServer;
    @Resource
    private PacketLogService packetLogService;

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {

    }

    /**
     * 连接
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final String address = ctx.channel().remoteAddress().toString().split(":")[0];
        log.info("【登录服务】 {} 已连接", address);

        //检测服务是否开启
        if (!checkChannelStatus(channelNo, channelType)) {
            ctx.channel().close();
            return;
        }
        // IV used to decrypt packets from client.
        final byte ivRecv[] = new byte[]{(byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255)};
        // IV used to encrypt packets for client.
        final byte ivSend[] = new byte[]{(byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255), (byte) Randomizer.nextInt(255)};

        final MapleClient client = new MapleClient(
                new PacketCrypto(ivSend, (short) (0xFFFF - AppConfigProperties.MAPLE_VERSION)), // Sent Cypher
                new PacketCrypto(ivRecv, AppConfigProperties.MAPLE_VERSION), // Recv Cypher
                ctx.channel());

        client.setChannelNo(channelNo);

        MaplePacketDecoder.DecoderState decoderState = new MaplePacketDecoder.DecoderState();
        ctx.channel().attr(MaplePacketDecoder.DECODER_STATE_KEY).set(decoderState);

        ctx.writeAndFlush(LoginPacketCreator.getHello(AppConfigProperties.MAPLE_VERSION, ivSend, ivRecv));
        ctx.channel().attr(MapleClient.CLIENT_KEY).set(client);
    }

    /**
     * 断开
     */
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        final MapleClient client = ctx.channel().attr(MapleClient.CLIENT_KEY).get();
        if (ObjectUtil.isNotNull(client)) {
            //登出


        }
    }


    /**
     * @description 检测服务是否开启
     * @author LouMT
     * @date 2026/2/10 17:11
     */
    private boolean checkChannelStatus(final int channelNo, ChannelTypeEnum channelType) {
        if (ObjectUtil.isNull(channelType)) return false;
        if (channelType == ChannelTypeEnum.CASH_SHOP_SERVER) return !cashShopServer.isShutDown();
        if (channelType == ChannelTypeEnum.LOGIN_SERVER) return !loginServer.isShutDown();
        if (channelType == ChannelTypeEnum.CHANNEL_SERVER)
            return !ChannelServerGroup.getChannelServer(channelNo).isShutDown();
        return true;
    }


    /**
     * 处理主要数据包
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object message) {
        final LittleEndianAccessor slea = new LittleEndianAccessor(new ByteArrayByteStream((byte[]) message));
        if (slea.available() < 2) return;

        final MapleClient client = ctx.channel().attr(MapleClient.CLIENT_KEY).get();
        if (ObjectUtil.isNull(client)) return;

        //记录数据包日志
        packetLogService.save(slea, client);

        //处理数据包
        final short headerValue = slea.readShort();
        for (final ReceivePacketOpcode opcode : ReceivePacketOpcode.values()) {
            if (headerValue == opcode.getValue()) {
                log.info("【PACKET】处理指令: {}", opcode);
                try {
                    var executor = PacketOperation.getExecutor(opcode);
                    if(ObjectUtil.isNull(executor)) {
                        log.info("【PACKET】发现未处理数据包 opcode:{} data:{}", opcode, slea.readMapleAsciiString());
                        break;
                    }
                    executor.invokeExact(slea, client);
                } catch (RejectedExecutionException e) {
                } catch (Throwable e) {
                }
            }
        }
    }

}
