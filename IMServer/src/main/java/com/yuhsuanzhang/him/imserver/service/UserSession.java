package com.yuhsuanzhang.him.imserver.service;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Component
public class UserSession {

    @Resource
    private RedissonClient redissonClient;

    /**
     * session前缀
     */
    private static final String SESSION_PREFIX = "session:";

    /**
     * session有效期（单位：秒）
     */
    private static final int SESSION_EXPIRE_SECONDS = 3600;

    /**
     * 创建session
     *
     * @param userId 用户ID
     * @return sessionId
     */
    public String createSession(Long userId) {
        String sessionId = UUID.randomUUID().toString().replace("-", "");
        RBucket<Long> bucket = redissonClient.getBucket(SESSION_PREFIX + sessionId);
        bucket.set(userId);
        bucket.expire(SESSION_EXPIRE_SECONDS, TimeUnit.SECONDS);
        return sessionId;
    }

    /**
     * 获取session中的用户ID
     *
     * @param sessionId sessionId
     * @return 用户ID
     */
    public Long getUserId(String sessionId) {
        RBucket<Long> bucket = redissonClient.getBucket(SESSION_PREFIX + sessionId);
        return bucket.get();
    }

    /**
     * 删除session
     *
     * @param sessionId sessionId
     */
    public void removeSession(String sessionId) {
        redissonClient.getBucket(SESSION_PREFIX + sessionId).delete();
    }

}
