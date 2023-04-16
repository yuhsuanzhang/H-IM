package com.yuhsuanzhang.him.imserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class IMServer {

    private final Map<String, ChannelHandlerContext> clients = new ConcurrentHashMap<>();
    private final Map<String, List<String>> groups = new ConcurrentHashMap<>();

    @Value("${im.server.port:8888}")
    private int port;

    @PostConstruct
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast((ChannelHandler) new IMHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("IMServer started and listening on " + future.channel().localAddress());

            future.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private class IMHandler extends SimpleChannelInboundHandler<String> {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            clients.put(ctx.channel().id().asLongText(),ctx);
            System.out.println("Client " + ctx.channel().id().asLongText() + " connected.");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            String clientId = ctx.channel().id().asLongText();
            clients.remove(clientId);
            System.out.println("Client " + clientId + " disconnected.");
            for (List<String> group : groups.values()) {
                group.remove(clientId);
            }
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String message) throws Exception {
            System.out.println("Received message [" + message + "]");
            String clientId = ctx.channel().id().asLongText();
            if (message.startsWith("@")) {
                // 私聊消息
                int index = message.indexOf(":");
                if (index > 0) {
                    String targetClientId = message.substring(1, index);
                    String content = message.substring(index + 1);
                    ChannelHandlerContext targetCtx = clients.get(targetClientId);
                    if (targetCtx != null) {
                        targetCtx.writeAndFlush("[" + clientId + "]: " + content + "\n");
                        ctx.writeAndFlush("You whispered to [" + targetClientId + "]: " + content + "\n");
                    } else {
                        ctx.writeAndFlush("No such client with ID " + targetClientId + "\n");
                    }
                } else {
                    ctx.writeAndFlush("Invalid message format for private chat, should be '@clientId:message'\n");
                }
            } else if (message.startsWith("#")) {
                // 群聊消息
                int index = message.indexOf(":");
                if (index > 0) {
                    String groupId = message.substring(1, index);
                    String content = message.substring(index + 1);
                    List<String> group = groups.get(groupId);
                    if (group != null) {
                        for (String memberId : group) {
                            if (!memberId.equals(clientId)) {
                                ChannelHandlerContext memberCtx = clients.get(memberId);
                                if (memberCtx != null) {
                                    memberCtx.writeAndFlush("[" + clientId + "]: " + content + "\"");
                                }
                            }
                            ctx.writeAndFlush("You sent a message to group " + groupId + ": " + content + "\n");
                        }
                    } else {
                        ctx.writeAndFlush("Invalid message format for group chat, should be '#groupId:message'\n");
                    }
                } else {
                    // 广播消息
                    for (ChannelHandlerContext clientCtx : clients.values()) {
                        if (clientCtx != ctx) {
                            clientCtx.writeAndFlush("[" + clientId + "]: " + message + "\n");
                        }
                    }
                }
            }

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.err.println("Client " + ctx.channel().id().asLongText() + " error: " + cause);
            ctx.close();
        }

    }

}