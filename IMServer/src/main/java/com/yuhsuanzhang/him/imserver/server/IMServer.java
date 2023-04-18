package com.yuhsuanzhang.him.imserver.server;

import com.yuhsuanzhang.him.imserver.handle.IMChannelInitializer;
import com.yuhsuanzhang.him.imserver.handle.IMHandler;
import com.yuhsuanzhang.him.imserver.handle.IMNioServerSocketChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class IMServer {

    @Value("${im.server.port:8888}")
    private int port;

    @Value("${im.server.id:zyx}")
    private String serverId;

    @Resource
    private IMChannelInitializer imChannelInitializer;

    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置bossGroup和workerGroup
            bootstrap.group(bossGroup, workerGroup)
                    // 设置NioServerSocketChannel为服务端channel类型
                    .channel(NioServerSocketChannel.class)
                    // 设置TCP连接数的请求等待队列的大小
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 设置TCP连接的keepalive属性为true，保持TCP连接的活跃状态
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 设置接受缓冲区大小
                    .childOption(ChannelOption.SO_RCVBUF, 32 * 1024)
                    // 设置发送缓冲区大小
                    .childOption(ChannelOption.SO_SNDBUF, 32 * 1024)
                    // 设置ChannelInitializer，在客户端连接上来的时候调用
                    .childHandler(imChannelInitializer);

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("IMServer started and listening on " + future.channel().localAddress());

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}