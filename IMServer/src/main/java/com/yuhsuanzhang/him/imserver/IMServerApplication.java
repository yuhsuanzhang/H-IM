package com.yuhsuanzhang.him.imserver;

import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistry;
import com.yuhsuanzhang.him.imserver.config.ZookeeperRegistryConfig;
import com.yuhsuanzhang.him.imserver.server.IMServer;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PreDestroy;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@SpringBootApplication
@EnableAsync
@EnableKafka
@EnableConfigurationProperties(ZookeeperRegistryConfig.class)
@MapperScan({"com.yuhsuanzhang.him.imserver.mapper"})
@Slf4j
public class IMServerApplication implements CommandLineRunner {

    @Value("${im.server.port:8888}")
    private int port;

    @Value("${im.service.name:zyx}")
    private String serviceName;

    @Value("${im.service.address:127.0.0.1}")
    private String serviceAddress;

    @Autowired
    private IMServer imServer;

    @Autowired
    private ZookeeperRegistry zookeeperRegistry;

    public static void main(String[] args) {
        SpringApplication.run(IMServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //先连接zookeeper,否则netty会将线程阻塞
        log.info("begin connect to Zookeeper");
        zookeeperRegistry.zookeeperRegistry();
        log.info("begin start Netty");
        imServer.run();
    }

    @PreDestroy
    public void destroy() {
        // 关闭Netty服务
        imServer.shutdown();
        try {
            // 从注册中心注销服务
            zookeeperRegistry.unregister(serviceName, String.format("%s:%s", serviceAddress, port));
            // 关闭Zookeeper连接
            zookeeperRegistry.close();
        } catch (Exception e) {
            log.error("Zookeeper connection close error [{}]", e.getMessage());
            e.printStackTrace();
        }
    }
}
