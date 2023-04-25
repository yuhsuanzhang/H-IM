package com.yuhsuanzhang.him.imserver.server;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = "test")
    public void listen(ConsumerRecord<String, String> record) {
        System.out.println("Received Message: " + record.value());
    }
}
