package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;
/**
 * @author LouMT
 * @name MapleNettyHandler
 * @date 2026-03-24 10:19
 * @email lmtemail163@163.com
 * @description
 */
public class MapleNettyHandler  extends ChannelInboundHandlerAdapter {

    /**
     * 当通道处于活动状态（连接成功）时被调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connected to server at: " + ctx.channel().remoteAddress());
        // 连接成功后，可以向服务器发送一条消息
        // ctx.writeAndFlush("Hello from client!\n");
    }

    /**
     * 当从服务器读取到数据时被调用
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            // 将接收到的字节转换为字符串并打印
            String receivedMessage = in.toString(StandardCharsets.UTF_8);
            System.out.print("Server said: " + receivedMessage);
        } finally {
            in.release(); // 重要：释放ByteBuf占用的内存
        }
    }

    /**
     * 当发生异常时被调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close(); // 发生异常时关闭连接
    }
}