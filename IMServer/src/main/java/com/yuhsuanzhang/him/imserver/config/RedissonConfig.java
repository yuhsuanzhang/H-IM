package com.yuhsuanzhang.him.imserver.config;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Configuration
@Slf4j
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

//    @Override
//    public void afterPropertiesSet() throws Exception {
//        if (redissonClient != null) {
//            return;
//        }
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://" + host + ":" + port);
//        if (!StringUtils.isEmpty(password)) {
//            config.useSingleServer().setPassword(password);
//        }
//        redissonClient = Redisson.create(config);
//        log.info("Redisson created");
//    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public RedissonClient redissonClient() {
        try {
            Config config = new Config();
            config.useSingleServer()
                    .setAddress("redis://" + host + ":" + port);
            if (!StringUtils.isEmpty(password)) {
                config.useSingleServer().setPassword(password);
            }
            RedissonClient redissonClient = Redisson.create(config);
            log.info("RedissonClient is created successfully");
            return redissonClient;
        } catch (Exception e) {
            log.error("Failed to create Redisson client: {}", e.getMessage());
        }
        log.error("Failed to create Redisson client");
        return null;
    }

}
