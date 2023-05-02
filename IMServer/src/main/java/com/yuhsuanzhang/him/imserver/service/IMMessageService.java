package com.yuhsuanzhang.him.imserver.service;

import com.yuhsuanzhang.him.imcommon.entity.IMMessage;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageExample;
import com.yuhsuanzhang.him.imcommon.util.IMMessageConvertor;
import com.yuhsuanzhang.him.imserver.mapper.IMMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Service
@Slf4j
public class IMMessageService {


    @Resource
    private IMMessageMapper imMessageMapper;
    @Resource
    private RedissonClient redissonClient;

    public IMMessage saveIMMessage(IMMessage imMessage) throws Exception {
        long id = redissonClient.getAtomicLong("im_message_id").incrementAndGet();
        imMessage.setId(id);
        int result = imMessageMapper.insert(imMessage);
        if (result != 1) {
            throw new Exception("保存IM消息失败");
        }
        return imMessage;
    }

    public boolean updateIMMessage(IMMessage imMessage) {
        //使用乐观锁 版本号进行更新
        int result = imMessageMapper.updateByVersionKeySelective(imMessage);
        if (result == 1) {
            return true;
        }
        return false;
    }

    public IMMessage getIMMessageById(long id) {
        IMMessage imMessage = imMessageMapper.selectByPrimaryKey(id);
        if (imMessage == null) {
            return null;
        }
        return imMessage;
    }

    public List<IMMessage> getIMMessageListByReceiverId(long receiverId) {
        IMMessageExample example = new IMMessageExample();
        example.createCriteria().andReceiverIdEqualTo(receiverId);
        List<IMMessage> imMessageList = imMessageMapper.selectByExample(example);
        if (imMessageList == null || imMessageList.isEmpty()) {
            return Collections.emptyList();
        }
        return imMessageList;
    }
}