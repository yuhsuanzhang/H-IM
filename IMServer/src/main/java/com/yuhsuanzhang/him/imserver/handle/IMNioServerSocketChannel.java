package com.yuhsuanzhang.him.imserver.handle;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.SocketUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
@Component
public class IMNioServerSocketChannel extends NioServerSocketChannel {

    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch =  SocketUtils.accept(javaChannel());

        try {
            if (ch != null) {
                // 此处重写ID
                NioSocketChannel nioSocketChannel = new IMNioSocketChannel(this, ch);
                buf.add(nioSocketChannel);
                return 1;
            }
        } catch (Throwable t) {
            log.warn("Failed to create a new channel from an accepted socket.", t);

            try {
                ch.close();
            } catch (Throwable t2) {
                log.warn("Failed to close a socket.", t2);
            }
        }

        return 0;
    }
}
