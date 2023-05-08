package com.yuhsuanzhang.him.imserver.config;

import com.yuhsuanzhang.him.imcommon.interfaces.Registry;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
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
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ZookeeperRegistry implements Registry {
    private CuratorFramework zkClient;
    private String basePath;

    @Resource
    private ZookeeperRegistryConfig zookeeperRegistryConfig;

    public void zookeeperRegistry() {
        log.info("ZookeeperRegistry zookeeperRegistryConfig:{}", zookeeperRegistryConfig);
        this.basePath = zookeeperRegistryConfig.getBasePath();
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(zookeeperRegistryConfig.getAddress())
                .sessionTimeoutMs(zookeeperRegistryConfig.getSessionTimeout())
                .connectionTimeoutMs(zookeeperRegistryConfig.getConnectionTimeout())
                .retryPolicy(new RetryNTimes(zookeeperRegistryConfig.getRetryTimes(),
                        zookeeperRegistryConfig.getSleepMsBetweenRetries()))
                .build();
        zkClient.start();
        log.info("Zookeeper started and address is {}", zookeeperRegistryConfig.getAddress());
    }

    private boolean checkSessionExpiration(String from) {
        //检查zookeeper是否连接
        try {
            if (zkClient.getState() != CuratorFrameworkState.STARTED) {
                zkClient.start();
                if (!zkClient.blockUntilConnected(30, TimeUnit.SECONDS)) {
                    throw new IllegalStateException("Zookeeper failed to start.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //线程等待一会Zookeeper client连上服务器
        for (int i = 0; i < 10; i++) {
            try {
                if (!zkClient.getZookeeperClient().isConnected()) {
                    Thread.sleep(100);
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error("Zookeeper register thread error [{}]", e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
        log.info("Zookeeper client state [{}] connected [{}]", zkClient.getState(), zkClient.getZookeeperClient().isConnected());
        //检查zookeeper session是否过期
        if (zkClient.getZookeeperClient().isConnected()) {
            try {
                zkClient.getZookeeperClient().getZooKeeper().exists("/", false);
                return true;
            } catch (Exception e) {
                log.error("Failed to check Zookeeper session expiration.", e);
                return false;
            }
        } else {
            log.error("Zookeeper is not connected. from [{}]", from);
            return false;
        }
    }

    private void checkSessionExpirationAndReRegister(String from) {
        // 检查zkClient的状态是否正常
        if (!checkSessionExpiration(from)) {
            // zkClient的session已经过期，需要重新获取session或者重新注册服务
            zookeeperReRegister();
        }
    }

    private void zookeeperReRegister() {
        //重新连接zookeeper
        try {
            this.close();
            zookeeperRegistry();
        } catch (Exception e) {
            log.error("Failed to re-register service.", e);
        }
    }


    @Override
    public void register(String serviceName, String serviceAddress) throws Exception {
        this.checkSessionExpirationAndReRegister("register");
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
        this.checkSessionExpirationAndReRegister("discover");
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
        this.checkSessionExpirationAndReRegister("unregister");
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