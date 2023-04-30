package com.yuhsuanzhang.him.imserver.controller;

import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistry;
import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistryConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@RestController
@Slf4j
public class IMController {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Resource
    private ZookeeperRegistry zookeeperRegistry;

    @Resource
    private ZookeeperRegistryConfig zookeeperRegistryConfig;

    @GetMapping("/meta")
    public String meta() {
        // 使用RMap替代ConcurrentHashMap
//        RMap<String, String> map = redissonClient.getMap("myMap");
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        try {
            List<String> list = zookeeperRegistry.discover("zyx");
            log.info("zk list:{}", list);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        kafkaTemplate.send("employee-change", map);
//        kafkaTemplate.send("employee-leave", "employee-leave");
        return "ok";
    }


}
