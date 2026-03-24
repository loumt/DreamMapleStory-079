package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * @author LouMT
 * @name MapleNettyClient
 * @date 2026-03-24 10:15
 * @email lmtemail163@163.com
 * @description 连接Netty仿客户机
 */
public class MapleNettyClient {

    private final String host;
    private final int port;

    public MapleNettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup(); // 创建一个线程组，用于处理I/O操作
        try {
            Bootstrap bootstrap = new Bootstrap(); // 客户端启动器
            bootstrap.group(group) // 指定线程组
                    .channel(NioSocketChannel.class) // 指定NIO传输模式
                    .handler(new ChannelInitializer<SocketChannel>() { // 指定Channel初始化时的操作
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 为pipeline添加处理器链
                            ch.pipeline()
                                    // 1. 使用换行符作为消息分隔符，解决粘包/半包问题
                                    .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    // 2. 将字节流解码为字符串
                                    .addLast(new StringDecoder())
                                    // 3. 将字符串编码为字节流
                                    .addLast(new StringEncoder())
                                    // 4. 添加我们自定义的处理器
                                    .addLast(new MapleNettyHandler());
                        }
                    });

            // 发起异步连接操作
            ChannelFuture future = bootstrap.connect(host, port).sync();

            // 获取连接的Channel
            Channel channel = future.channel();

            // 从控制台读取用户输入并发送到服务器
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String userInput = in.readLine();
                if (userInput == null || "quit".equalsIgnoreCase(userInput)) {
                    break; // 用户输入quit退出循环
                }
                channel.writeAndFlush(userInput + "\n"); // 发送消息，并加上换行符以配合DelimiterBasedFrameDecoder
            }

            // 等待连接关闭
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully(); // 关闭线程组，释放资源
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String host = "localhost";
        int port = 8080;
        new MapleNettyClient(host, port).run();
    }


}
