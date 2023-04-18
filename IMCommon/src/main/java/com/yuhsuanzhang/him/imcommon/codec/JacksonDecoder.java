package com.yuhsuanzhang.him.imcommon.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
public class JacksonDecoder extends LengthFieldBasedFrameDecoder {
    private ObjectMapper objectMapper = new ObjectMapper();

    public JacksonDecoder() {
        super(Integer.MAX_VALUE, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        log.info("decode ByteBuf:{}",in);
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        int length = frame.readInt();
        byte[] bytes = new byte[length];
        frame.readBytes(bytes);
        return objectMapper.readValue(bytes, Object.class);
    }
}
