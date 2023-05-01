package com.yuhsuanzhang.him.imclient.handle;

import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author yuxuan.zhang
 * @Description
 */
public class IMClientHandler extends SimpleChannelInboundHandler<IMMessageProto.IMMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessageProto.IMMessage msg) throws Exception {
        System.out.println(msg);
    }
}
