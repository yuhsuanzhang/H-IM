package com.yuhsuanzhang.him.imserver.controller;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@RestController
public class IMController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @GetMapping("/meta")
    public String meta() {
        // 使用RMap替代ConcurrentHashMap
//        RMap<String, String> map = redissonClient.getMap("myMap");
        Map<String,String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        kafkaTemplate.send("test", map);
        return "ok";
    }


}
