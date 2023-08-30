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
}
