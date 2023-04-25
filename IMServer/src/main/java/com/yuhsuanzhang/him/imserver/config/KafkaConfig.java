package com.yuhsuanzhang.him.imserver.config;

import lombok.Data;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.config.SslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yuxuan.zhang
 * @Description
 */
//@Configuration
//@EnableKafka
//@Data
//@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConfig {

//    private String bootstrapServers;
//    private Producer producer;
//    private Consumer consumer;
//    private Sasl sasl;
//
//    @Data
//    public static class Producer {
//        private String keySerializer;
//        private String valueSerializer;
//        private String acks;
//        private int retries;
//        private int batchSize;
//        private int lingerMs;
//        private int bufferMemory;
//        private int maxBlockMs;
//        private boolean enableIdempotence;
//        private String compressionType;
//        private int maxInFlightRequestsPerConnection;
//        private int requestTimeoutMs;
//
//        // getters and setters
//    }
//
//    @Data
//    public static class Consumer {
//        private String keySerializer;
//        private String valueSerializer;
//        private String groupId;
//        private String autoOffsetReset;
//        private int maxPollRecords;
//
//        // getters and setters
//    }
//
//    @Data
//    public static class Sasl {
//        private String mechanism;
//        private String jaasConfig;
//
//        // getters and setters
//    }
//
//    // getters and setters
//    // ...
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producer.keySerializer);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producer.valueSerializer);
//        props.put(ProducerConfig.ACKS_CONFIG, producer.acks);
//        props.put(ProducerConfig.RETRIES_CONFIG, producer.retries);
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, producer.batchSize);
//        props.put(ProducerConfig.LINGER_MS_CONFIG, producer.lingerMs);
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, producer.bufferMemory);
//        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, producer.maxBlockMs);
//        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, producer.enableIdempotence);
//        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, producer.compressionType);
//        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, producer.maxInFlightRequestsPerConnection);
//        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, producer.requestTimeoutMs);
//        props.put(SaslConfigs.SASL_MECHANISM, sasl.getMechanism());
//        props.put(SaslConfigs.SASL_JAAS_CONFIG, sasl.getJaasConfig());
//        return new DefaultKafkaProducerFactory<>(props);
//    }
//
//    @Bean
//    public ConsumerFactory<String, Object> consumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, consumer.groupId);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumer.autoOffsetReset);
//        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, consumer.maxPollRecords);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.keySerializer);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,consumer.valueSerializer);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        return new DefaultKafkaConsumerFactory<>(props);
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
}

