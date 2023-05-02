package com.yuhsuanzhang.him.imserver.config;

import com.yuhsuanzhang.him.imcommon.interfaces.Registry;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.CreateMode;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ZookeeperRegistry implements Registry {
    private CuratorFramework zkClient;
    private String basePath;

    @Resource
    private ZookeeperRegistryConfig zookeeperRegistryConfig;

//    public ZookeeperRegistry(CuratorFramework zkClient, String basePath) {
//        this.zkClient = zkClient;
//        this.basePath = basePath;
//    }

    public void zookeeperRegistry() {
        log.info("ZookeeperRegistry zookeeperRegistryConfig:{}", zookeeperRegistryConfig);
        this.basePath = zookeeperRegistryConfig.getBasePath();
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(zookeeperRegistryConfig.getAddress())
                .sessionTimeoutMs(zookeeperRegistryConfig.getSessionTimeout())
                .connectionTimeoutMs(zookeeperRegistryConfig.getConnectionTimeout())
                .retryPolicy(new RetryNTimes(zookeeperRegistryConfig.getRetryTimes(), zookeeperRegistryConfig.getSleepMsBetweenRetries()))
                .build();
        zkClient.start();
        log.info("Zookeeper started and address is {}", zookeeperRegistryConfig.getAddress());
    }


    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        // 创建服务节点
        String servicePath = basePath + "/" + serviceName;
        if (zkClient.checkExists().forPath(servicePath) == null) {
            zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath);
        }

        // 创建临时节点
        String addressPath = servicePath + "/" + serviceAddress;
        if (zkClient.checkExists().forPath(addressPath) == null) {
            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
        } else {
            // 节点已经存在，先删除再创建
            log.warn("Zookeeper node exist serviceName [{}] serviceAddress [{}]", serviceName, serviceAddress);
            zkClient.delete().forPath(addressPath);
            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(addressPath);
        }
        log.info("Zookeeper register serviceName [{}] serviceAddress [{}]", serviceName, serviceAddress);
    }

    @Override
    public List<String> discover(String serviceName) throws Exception {
        // 获取服务节点下的所有地址节点
        String servicePath = basePath + "/" + serviceName;
        List<String> addressNodes = zkClient.getChildren().forPath(servicePath);

        // 将地址节点转换为地址和端口
        List<String> addresses = new ArrayList<>();
        for (String addressNode : addressNodes) {
            log.info("Zookeeper discover servicePath [{}] addressNode [{}]", servicePath, addressNode);
            addresses.add(addressNode);
        }
        return addresses;
    }

    @Override
    public void unregister(String serviceName, String serviceAddress) throws Exception {
        String addressPath = basePath + "/" + serviceName + "/" + serviceAddress;
        zkClient.delete().guaranteed().deletingChildrenIfNeeded().forPath(addressPath);
        log.info("Zookeeper unregister addressPath [{}]", addressPath);
    }

    @Override
    public void close() throws Exception {
        zkClient.close();
        log.info("Zookeeper closed");
    }
}