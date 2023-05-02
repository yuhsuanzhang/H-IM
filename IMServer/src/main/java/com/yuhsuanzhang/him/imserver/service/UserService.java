package com.yuhsuanzhang.him.imserver.service;

import com.yuhsuanzhang.him.imcommon.entity.User;
import com.yuhsuanzhang.him.imcommon.entity.UserExample;
import com.yuhsuanzhang.him.imserver.mapper.UserMapper;
import com.yuhsuanzhang.him.imserver.util.HashUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
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
            if (userList == null || userList.size() == 0) return null;
            return userList.get(0);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public boolean insert(User user) {
        if (user == null || StringUtils.isEmpty(user.getAccount()) || StringUtils.isEmpty(user.getPassword())) {
            return false;
        }
        boolean result = false;
        try {
            user.setPassword(HashUtil.hash(user.getPassword()));
            //建账号为唯一索引，故插入不需要锁
            result = userMapper.insert(user) > 0;
        } catch (Exception e) {
            log.error("Insert into table user error [{}] ", e.getMessage());
            e.printStackTrace();
        } finally {
        }
        return result;
    }
}
