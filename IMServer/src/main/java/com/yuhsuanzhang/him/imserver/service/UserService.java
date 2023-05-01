package com.yuhsuanzhang.him.imserver.service;

import com.yuhsuanzhang.him.imcommon.entity.User;
import com.yuhsuanzhang.him.imcommon.entity.example.UserExample;
import com.yuhsuanzhang.him.imserver.mapper.UserMapper;
import com.yuhsuanzhang.him.imserver.util.HashUtil;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedissonClient redissonClient;

    public User getUserById(Long id) {
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("user:" + id);
        rwLock.readLock().lock();
        try {
            return userMapper.selectByPrimaryKey(id.intValue());
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public User getUserByAccount(String account) {
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("user:" + account);
        rwLock.readLock().lock();
        try {
            UserExample example = new UserExample();
            example.createCriteria().andAccountEqualTo(account);
            List<User> userList = userMapper.selectByExample(example);
            if (userList != null && !userList.isEmpty()) {
                return userList.get(0);
            }
            return null;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public boolean checkPassword(String account, String password) {
        password = HashUtil.hash(password);
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("user:" + account);
        rwLock.readLock().lock();
        try {
            UserExample example = new UserExample();
            example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(password);
            long count = userMapper.countByExample(example);
            return count > 0;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public User getUser(String account, String password) {
        password = HashUtil.hash(password);
        RReadWriteLock rwLock = redissonClient.getReadWriteLock("user:" + account);
        rwLock.readLock().lock();
        try {
            UserExample example = new UserExample();
            example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(password);
            List<User> userList = userMapper.selectByExample(example);
            return userList.get(0);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public boolean insert(User user) {
        if (user == null || StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())) {
            return false;
        }
        user.setPassword(HashUtil.hash(user.getPassword()));
        RLock lock = redissonClient.getLock("user:insert");
        boolean result = false;
        try {
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                result = userMapper.insert(user) > 0;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return result;
    }
}
