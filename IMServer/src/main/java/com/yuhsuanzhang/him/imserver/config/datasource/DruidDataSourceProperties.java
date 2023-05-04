package com.yuhsuanzhang.him.imserver.config.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidDataSourceProperties {
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private long maxWait;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String validationQuery;
    private int validationQueryTimeout;
    private boolean testWhileIdle;
    private long maxEvictableIdleTimeMillis;
    private long minEvictableIdleTimeMillis;
    private long timeBetweenEvictionRunsMillis;
    private boolean breakAfterAcquireFailure;
    private int connectionErrorRetryAttempts;
    private boolean logAbandoned;
    private long removeAbandonedTimeoutMillis;
}

