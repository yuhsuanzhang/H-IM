package com.yuhsuanzhang.him.imserver.config;

import io.netty.channel.ChannelHandlerContext;
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
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Configuration
public class RedissonConfig implements InitializingBean {

    private static RedissonClient redissonClient;

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redissonClient != null) {
            return;
        }
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port);
        if (!StringUtils.isEmpty(password)) {
            config.useSingleServer().setPassword(password);
        }
        System.out.println("redisson created");
        redissonClient = Redisson.create(config);
    }

    @Bean
    @Order(1)
    public RedissonClient redissonClient() {
        if (redissonClient == null) {
            try {
                afterPropertiesSet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return redissonClient;
    }

    @Bean
    public RMap<String, ChannelHandlerContext> clients(RedissonClient redissonClient) {
        return redissonClient.getMap("clients");
    }

    @Bean
    public RMap<String, List<String>> groups(RedissonClient redissonClient) {
        return redissonClient.getMap("groups");
    }
}
//}
//
//    /**
//     * 配置 Redisson 客户端
//     * @return
//     */
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson() {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://" + host + ":" + port);
//        if (!StringUtils.isEmpty(password)) {
//            config.useSingleServer().setPassword(password);
//        }
//        System.out.println("redisson config");
//        return Redisson.create(config);
//    }
//}
