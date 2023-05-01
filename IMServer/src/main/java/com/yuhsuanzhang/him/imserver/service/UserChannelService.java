package com.yuhsuanzhang.him.imserver.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class UserChannelService {
    @Resource
    private RedissonClient redissonClient;

    @Value("${im.server.id:zyx}")
    private String serverId;

    // 获取 channel id 和用户 id 的 map，其中哈希表的 key 为 channel id，field 为设备号，value 为用户 id
    //key channel id
    //field 设备号
    //value 用户 id
    RMap<String, RMap<Integer, Long>> channelUserMap = redissonClient.getMap("channelUserMap");

    // 定义本地内存中的 channel id 对应 channel 的 map
    //key channel id
    //value ChannelHandlerContext
    ConcurrentHashMap<String, ChannelHandlerContext> channelIdChannelMap = new ConcurrentHashMap<>();

    // 获取 channel id 和服务器 id 的 map，其中 key 为 channel id，value 为服务器 id
    //key channel id
    //value 服务器 id
    RMap<String, String> channelServerMap = redissonClient.getMap("channelServerMap");

    // 登录操作，将 channel id 与用户 id 存储到 channelUserMap 中
    public void login(ChannelHandlerContext ctx, Integer deviceId, Long userId) {
        String channelId = ctx.channel().id().asLongText();
        // 将 channel id 与用户 id 存储到 channelUserMap 中
        RMap<Integer, Long> userMap = channelUserMap.get(channelId);
        if (userMap == null) {
            userMap = redissonClient.getMap(channelId);
            channelUserMap.put(channelId, userMap);
        }
        userMap.put(deviceId, userId);

        // 将 channel id 与 channel 存储到 channelIdChannelMap 中
        channelIdChannelMap.put(channelId, ctx);

        // 将 channel id 与服务器 id 存储到 channelServerMap 中
//        String serverId = null;
//        try {
//            serverId = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
        channelServerMap.put(channelId, serverId);
    }

    // 注销操作，从 channelUserMap 中移除 channel id 与用户 id 的映射
    public void logout(ChannelHandlerContext ctx, String deviceId) {
        String channelId = ctx.channel().id().asLongText();
        // 从 channelUserMap 中移除 channel id 与用户 id 的映射
        RMap<Integer, Long> userMap = channelUserMap.get(channelId);
        if (userMap != null) {
            userMap.remove(deviceId);
            if (userMap.isEmpty()) {
                channelUserMap.remove(channelId);
            }
        }

        // 从 channelMap 中移除 channel id 与 ChannelHandlerContext 的映射
        channelIdChannelMap.remove(channelId);

        // 将 channel id 与服务器 id map channelServerMap 中 channel id移除
        channelServerMap.remove(channelId);
    }

    // 获取 channelUserMap 中指定 channelId 的 userMap
    RMap<String, String> getUserMap(String channelId, String device) {
        RMap<String, RMap<String, String>> map = redissonClient.getMap("channelUserMap");
        RMap<String, String> userMap = map.get(channelId);
        if (userMap == null) {
            userMap = map.putIfAbsent(channelId, redissonClient.getMap(String.format("%s:%s", channelId, device)));
            if (userMap == null) {
                userMap = map.get(channelId);
            }
        }
        return userMap;
    }

    // 移除 channelUserMap 中指定 channelId 的 userMap
    void removeUserMap(String channelId) {
        RMap<String, RMap<String, String>> map = redissonClient.getMap("channelUserMap");
        map.remove(channelId);
    }

    // 获取 channelIdChannelMap 中指定 channelId 的 ChannelHandlerContext
    ChannelHandlerContext getChannelHandlerContext(String channelId) {
        return channelIdChannelMap.get(channelId);
    }

    // 添加 channelIdChannelMap 的映射关系
    void putChannelHandlerContext(String channelId, ChannelHandlerContext ctx) {
        channelIdChannelMap.put(channelId, ctx);
    }

    // 移除 channelIdChannelMap 中指定 channelId 的映射关系
    void removeChannelHandlerContext(String channelId) {
        channelIdChannelMap.remove(channelId);
    }

    // 获取 channelServerMap 中指定 channelId 的服务器 id
    String getServerId(String channelId) {
        return channelServerMap.get(channelId);
    }

    // 设置 channelServerMap 中指定 channelId 的服务器 id
    void setServerId(String channelId, String serverId) {
        channelServerMap.put(channelId, serverId);
    }

    // 移除 channelServerMap 中指定 channelId 的服务器 id
    void removeServerId(String channelId) {
        channelServerMap.remove(channelId);
    }

}