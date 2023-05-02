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
        RMap<String, String> maps = redissonClient.getMap("myMap");
        Map<String, String> map = new ConcurrentHashMap<>();
        maps.put("key1", "value1");
        maps.put("key2", "value2");
        RMap<String,String> m = redissonClient.getMap("myMap");
        log.info("m list:{}", m.keySet());
        maps.put("key3", "value2");
        RMap<String,String> m2 = redissonClient.getMap("myMap");
        log.info("m2 list:{}", m2.keySet());
        try {
            List<String> list = zookeeperRegistry.discover("zyx");
            log.info("zk list:{}", list);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        kafkaTemplate.send("employee", map);
//        kafkaTemplate.send("employee-leave", "employee-leave");
        return "ok";
    }


}
