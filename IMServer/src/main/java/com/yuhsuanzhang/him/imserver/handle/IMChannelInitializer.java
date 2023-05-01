package com.yuhsuanzhang.him.imserver.handle;

import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class IMChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Resource
    private IMHandler imHandler;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new ProtobufDecoder(IMMessageProto.IMMessage.getDefaultInstance()))
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                .addLast(new ProtobufEncoder())
                .addLast(imHandler);
    }
}
