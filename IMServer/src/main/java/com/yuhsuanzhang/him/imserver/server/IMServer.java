package com.yuhsuanzhang.him.imserver.server;

import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistry;
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
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Slf4j
public class IMServer {

    @Value("${im.server.port:8888}")
    private int port;

    @Value("${im.server.id:zyx}")
    private String serverId;

    @Value("${im.service.name:zyx}")
    private String serviceName;

    @Value("${im.service.address:127.0.0.1}")
    private String serviceAddress;

    @Resource
    private IMChannelInitializer imChannelInitializer;

    @Resource
    private ZookeeperRegistry zookeeperRegistry;
    private final EventLoopGroup bossGroup = new NioEventLoopGroup();
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void run() throws Exception {
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
            log.info("IMServer started and listening on " + future.channel().localAddress());

            // 注册服务到Zookeeper
            zookeeperRegistry.register(serviceName, String.format("%s:%s", serviceAddress, port));

            future.channel().closeFuture().sync();
        } finally {
        }
    }

    public void shutdown() {
        // 关闭Netty服务
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        log.info("IMServer ended");
    }
}