package com.yuhsuanzhang.him.imserver.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class NettyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private IMServer imServer;

    @Override
    public void run(String... args) throws Exception {
        imServer.run();
    }
}
