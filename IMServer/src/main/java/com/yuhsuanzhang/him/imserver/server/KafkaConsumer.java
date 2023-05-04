package com.yuhsuanzhang.him.imserver.server;

import com.google.protobuf.InvalidProtocolBufferException;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "kafka-json", groupId = "${spring.kafka.consumer.json-group-id}", containerFactory = "jsonKafkaListenerContainerFactory")
    public void listen1(ConsumerRecord<String, Object> record) {
        System.out.println("Received Message first value: " + record.value());
    }

    @KafkaListener(topics = "kafka-byte", groupId = "${spring.kafka.consumer.byte-array-group-id}", containerFactory = "byteArrayKafkaListenerContainerFactory")
    public void listen2(ConsumerRecord<String, byte[]> record) {
        try {
            System.out.println("Received Message first value: " + IMMessageProto.IMMessage.parseFrom((byte[]) record.value()));
        } catch (Exception e) {
            log.error("Error in listen2", e);

        }
    }

//    @KafkaListener(topics = "kafka-test1", groupId = "${spring.kafka.consumer.byte-array-group-id}", containerFactory = "byteArrayKafkaListenerContainerFactory")
//    public void listen3(ConsumerRecord<String, byte[]> record) {
//        try {
//            System.out.println("33333333Received Message first value: " + IMMessageProto.IMMessage.parseFrom((byte[]) record.value()));
//        } catch (Exception e) {
//            log.error("Error in listen2", e);
//
//        }
//    }
//
//    @KafkaListener(topics = "kafka-test", groupId = "${spring.kafka.consumer.byte-array-group-id}", containerFactory = "jsonKafkaListenerContainerFactory")
//    public void listen4(ConsumerRecord<String, Object> record) {
//        try {
//            System.out.println("4444444Received Message first value: " +record.value());
//        } catch (Exception e) {
//            log.error("Error in listen2", e);
//
//        }
//    }
//
//    @KafkaListener(topics = "kafka-leave", groupId = "bitmain")
//    public void listen11(ConsumerRecord<String, String> record) {
//        System.out.println("leave first value: " + record.value());
//    }
//
//    @KafkaListener(topics = "kafka-change", groupId = "ssc")
//    public void listen2(ConsumerRecord<String, String> record) {
//        System.out.println("Received Message second value: " + record.value());
//    }
//
//    @KafkaListener(topics = "kafka-leave", groupId = "ssc")
//    public void listen22(ConsumerRecord<String, String> record) {
//        System.out.println("leave second value: " + record.value());
//    }
//
//    @KafkaListener(topics = "kafka-change", groupId = "sophgo")
//    public void listen3(ConsumerRecord<String, String> record) {
//        System.out.println("Received Message third value: " + record.value());
//    }
//
//    @KafkaListener(topics = "kafka-leave", groupId = "sophgo")
//    public void listen33(ConsumerRecord<String, String> record) {
//        System.out.println("leave third value: " + record.value());
//    }
}
