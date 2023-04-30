package com.yuhsuanzhang.him.imserver.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "spring.zookeeper")
public
class ZookeeperRegistryConfig {
    private String address;
    private String basePath;
    private int sessionTimeout;
    private int connectionTimeout;
    private int retryTimes;
    private int sleepMsBetweenRetries;
}

