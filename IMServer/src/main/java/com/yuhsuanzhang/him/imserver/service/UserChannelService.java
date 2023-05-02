package com.yuhsuanzhang.him.imserver.service;

import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;
import com.yuhsuanzhang.him.imcommon.enums.MessageTypeEnum;
import com.yuhsuanzhang.him.imserver.config.RedissonConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RTransaction;
import org.redisson.api.RedissonClient;
import org.redisson.api.TransactionOptions;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
@Slf4j
public class UserChannelService {

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private TransactionTemplate transactionTemplate;
//    private RedissonClient redissonClient = RedissonConfig.getRedissonClient();

//    public void setRedissonClient(RedissonClient redissonClient) {
//        this.redissonClient = redissonClient;
//    }

    public UserChannelService() {
//        Config config = new Config();
//        config.useSingleServer()
//                .setAddress("redis://" + "127.0.0.1" + ":" + "6379");
//        if (!StringUtils.isEmpty(password)) {
//            config.useSingleServer().setPassword(password);
//        }
//        System.out.println("312453214231");
//        redissonClient = Redisson.create(config);
    }

    @Value("${im.server.id:zyx}")
    private String serverId;

    // 定义存储结构的前缀
    private static final String CHANNEL_USER_DEVICE = "CHANNEL_USER_DEVICE";
    private static final String USER_DEVICE_CHANNEL = "USER_DEVICE_CHANNEL";
    private static final String CHANNEL_SERVER = "CHANNEL_SERVER";

    // 获取 channel id 和用户 id 的 map，其中哈希表的 key 为 channel id，field 为设备号，value 为用户 id
    //key channel id
    //field 设备号
    //value 用户 id
//    RMap<String, RMap<Integer, Long>> channelUserMap = redissonClient.getMap("channelUserMap");
    //key channel id
    //value 用户 id: 用户设备 id
    RMap<String, String> channelUserDeviceMap;

    //key 用户 id: 用户设备 id
    //value channel id
    RMap<String, String> userDeviceChannelMap;

    // 定义本地内存中的 channel id 对应 channel 的 map
    //key channel id
    //value ChannelHandlerContext
    ConcurrentHashMap<String, ChannelHandlerContext> channelIdChannelMap = new ConcurrentHashMap<>();

    // 获取 channel id 和服务器 id 的 map，其中 key 为 channel id，value 为服务器 id
    //key channel id
    //value 服务器 id
    RMap<String, String> channelServerMap;

    @PostConstruct
    public void init() {
        // 在依赖注入完成后，容器初始化时执行的逻辑
        userDeviceChannelMap = redissonClient.getMap(USER_DEVICE_CHANNEL);
        channelUserDeviceMap = redissonClient.getMap(CHANNEL_USER_DEVICE);
        channelServerMap = redissonClient.getMap(CHANNEL_SERVER);
        log.info("UserChannelService init end userDeviceChannelMap [{}] channelUserDeviceMap [{}] channelServerMap [{}]", userDeviceChannelMap, channelUserDeviceMap, channelServerMap);
    }

    public void login(ChannelHandlerContext ctx, Integer deviceId, Long userId) {
        // 将 Channel 和 userId/deviceId 映射关系存入 Redis
        String channelKey = ctx.channel().id().asLongText();
        String userDeviceKey = userId + "/" + deviceId;
        String oldChannelKey = userDeviceChannelMap.get(userDeviceKey);
        transactionTemplate.execute(status -> {
            // 如果该用户已经在其他设备登录，则通知其他设备下线
            if (oldChannelKey != null) {
                ChannelHandlerContext oldCtx = channelIdChannelMap.get(oldChannelKey);
                if (oldCtx != null && oldCtx.channel().isActive()) {
                    // 向旧 Channel 发送下线通知
                    oldCtx.writeAndFlush(IMMessageProto.IMMessage.newBuilder()
                            .setMessageType(MessageTypeEnum.LOGOUT.getCode())
                            .setContent("您的账号在其他设备登录，您已被迫下线！\n")
                            .build());
                    oldCtx.close();
                }
            }
            channelUserDeviceMap.put(channelKey, userDeviceKey);
            userDeviceChannelMap.put(userDeviceKey, ctx.channel().id().asLongText());
            // 将 Channel 和 ChannelHandlerContext 映射关系存入本地 map
            channelIdChannelMap.put(channelKey, ctx);
            // 更新 Channel 对应的服务器 ID
            channelServerMap.put(channelKey, serverId);
            return null;
        });
    }


    public void logout(ChannelHandlerContext ctx) {
        // 从 Redis 中删除 Channel 和 userId:deviceId 映射关系
        String channelKey = ctx.channel().id().asLongText();
        String userDeviceKey = channelUserDeviceMap.get(channelKey);
        transactionTemplate.execute(status -> {
            channelUserDeviceMap.remove(channelKey);
            userDeviceChannelMap.remove(userDeviceKey);

            // 从本地 map 中删除 Channel 和 ChannelHandlerContext 映射关系
            channelIdChannelMap.remove(channelKey);

            // 更新 Channel 对应的服务器 ID
            channelServerMap.remove(channelKey);
            return null;
        });
    }

    public Long getUserIdByChannelId(String channelId) {
        String userDevice = channelUserDeviceMap.get(channelId);
        String[] parts = userDevice.split("/");
        if (parts.length == 2) {
            return Long.parseLong(parts[0]);
        }
        return null;
    }

}