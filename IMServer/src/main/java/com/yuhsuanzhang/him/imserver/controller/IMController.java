package com.yuhsuanzhang.him.imserver.controller;

import com.yuhsuanzhang.him.imcommon.entity.IMMessage;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistry;
import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistryConfig;
import com.yuhsuanzhang.him.imserver.service.IMMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
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
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplateJson;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplateByte;

    @Resource
    private ZookeeperRegistry zookeeperRegistry;

    @Resource
    private IMMessageService imMessageService;

    @Resource
    private ZookeeperRegistryConfig zookeeperRegistryConfig;

    @GetMapping("/meta")
    public String meta() throws Exception {
        Connection connection = sqlSessionTemplate.getConnection();
        // 使用RMap替代ConcurrentHashMap
        RMap<String, String> maps = redissonClient.getMap("myMap");
        Map<String, String> map = new ConcurrentHashMap<>();
        maps.put("key2", "value2");
        RMap<String, String> m = redissonClient.getMap("myMap");
        log.info("m list:{}", m.keySet());
        maps.put("key3", "value2");
        RMap<String, String> m2 = redissonClient.getMap("myMap");
        log.info("m2 list:{}", m2.keySet());
        imMessageService.saveIMMessage(IMMessage.builder()
                .id(1L)
                .content("hello world")
                .senderId(1L)
                .receiverId(2L)
                .previousMessageId(0L)
                .receiverType(0)
                .messageType(0)
                .sendTime(LocalDateTime.now())
                .deviceType(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .version(0)
                .build());
        IMMessage imMessage = imMessageService.getIMMessageById(7);
//        log.info("IMMessage : [{}]", imMessage);
        try {
            List<String> list = zookeeperRegistry.discover("zyx");
//            log.info("zk list:{}", list);
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        IMMessageProto.IMMessage imMessage1 = IMMessageProto.IMMessage.newBuilder().setContent("hello").build();
        map.put("key1", "value1");
        kafkaTemplateJson.send("kafka-json", map);
        kafkaTemplateByte.send("kafka-byte", imMessage1.toByteArray());
//        kafkaTemplateJson.send("employee-test", map);
//        kafkaTemplateByte.send("employee-test1", imMessage1.toByteArray());
//        kafkaTemplate.send("employee-leave", "employee-leave");
        return "ok";
    }


}
