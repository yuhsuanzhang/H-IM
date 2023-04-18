package com.yuhsuanzhang.him.imcommon.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonEncoder extends MessageToByteEncoder<Object> {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf out) throws Exception {
        log.info("decode ByteBuf:{}",out);
        byte[] bytes = objectMapper.writeValueAsBytes(obj);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
//        ctx.writeAndFlush(out);
    }
}

