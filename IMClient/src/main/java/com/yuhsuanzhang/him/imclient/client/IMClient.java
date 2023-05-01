package com.yuhsuanzhang.him.imclient.client;

import com.yuhsuanzhang.him.imclient.handle.IMClientHandler;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class IMClient {

    @Value("${im.server.host:127.0.0.1}")
    private String host;
    @Value("${im.server.port:8888}")
    private int port;

    @PostConstruct
    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    .addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(IMMessageProto.IMMessage.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast((ChannelHandler) new IMClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            System.out.println("Connected to server on " + host + ":" + port);

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String line = in.readLine();
                if (line == null || line.length() == 0 || line.trim().length() == 0) {
                    continue;
                }
                IMMessageProto.IMMessage imMessage = IMMessageProto
                        .IMMessage
                        .newBuilder()
                        .setContent(line)
                        .build();
                future.channel().writeAndFlush(imMessage);
            }
        } finally {
            group.shutdownGracefully();
        }
    }
}
