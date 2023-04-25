package com.yuhsuanzhang.him.imserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@SpringBootApplication
@EnableAsync
@EnableKafka
public class IMServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }

}
