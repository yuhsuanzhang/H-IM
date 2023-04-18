package com.yuhsuanzhang.him.imserver.handle;

import com.yuhsuanzhang.him.imserver.util.ChannelIdUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;
import java.util.UUID;


/**
 * @Author yuxuan.zhang
 * @Description
 */
public class IMNioSocketChannel extends NioSocketChannel {


    public IMNioSocketChannel(Channel parent, SocketChannel socket) {
        super(parent, socket);
    }

    // 自定义ID
    @Override
    protected ChannelId newId() {
        return new ChannelId() {
            private String id = ChannelIdUtil.get();

            @Override
            public String asShortText() {
                return id;
            }

            @Override
            public String asLongText() {
                return id;
            }

            @Override
            public int compareTo(ChannelId o) {
                return id.equals(o.asLongText()) ? 1 : 0;
            }
        };
    }
}
