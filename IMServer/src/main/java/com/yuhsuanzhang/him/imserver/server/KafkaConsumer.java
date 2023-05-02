package com.yuhsuanzhang.him.imserver.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "employee", groupId = "bitmain")
    public void listen1(ConsumerRecord<String, Object> record) {
        System.out.println("Received Message first value: " + record.value());
    }
//
//    @KafkaListener(topics = "employee-leave", groupId = "bitmain")
//    public void listen11(ConsumerRecord<String, String> record) {
//        System.out.println("leave first value: " + record.value());
//    }
//
//    @KafkaListener(topics = "employee-change", groupId = "ssc")
//    public void listen2(ConsumerRecord<String, String> record) {
//        System.out.println("Received Message second value: " + record.value());
//    }
//
//    @KafkaListener(topics = "employee-leave", groupId = "ssc")
//    public void listen22(ConsumerRecord<String, String> record) {
//        System.out.println("leave second value: " + record.value());
//    }
//
//    @KafkaListener(topics = "employee-change", groupId = "sophgo")
//    public void listen3(ConsumerRecord<String, String> record) {
//        System.out.println("Received Message third value: " + record.value());
//    }
//
//    @KafkaListener(topics = "employee-leave", groupId = "sophgo")
//    public void listen33(ConsumerRecord<String, String> record) {
//        System.out.println("leave third value: " + record.value());
//    }
}
