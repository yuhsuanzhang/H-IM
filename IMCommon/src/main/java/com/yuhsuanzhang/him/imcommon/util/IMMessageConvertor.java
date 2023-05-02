package com.yuhsuanzhang.him.imcommon.util;

import com.yuhsuanzhang.him.imcommon.entity.IMMessage;
import com.yuhsuanzhang.him.imcommon.entity.IMMessageProto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * @Author yuxuan.zhang
 * @Description
 */

public class IMMessageConvertor {

    public static IMMessage fromProto(IMMessageProto.IMMessage proto) {
        IMMessage message = IMMessage.builder()
                .id(proto.getId())
                .content(proto.getContent())
                .senderId(proto.getSenderId())
                .receiverId(proto.getReceiverId())
                .previousMessageId(proto.getPreviousMessageId())
                .receiverType(proto.getReceiverType())
                .messageType(proto.getMessageType())
                .sendTime(TimestampConverter.toLocalDateTime(proto.getSendTime()))
                .deviceType(proto.getDeviceType())
                .createTime(TimestampConverter.toLocalDateTime(proto.getCreateTime()))
                .updateTime(TimestampConverter.toLocalDateTime(proto.getUpdateTime()))
                .version(proto.getVersion()).build();
        return message;
    }

    public static IMMessageProto.IMMessage toProto(IMMessage message) {
        IMMessageProto.IMMessage.Builder builder = IMMessageProto.IMMessage.newBuilder();
        builder.setId(message.getId())
                .setContent(message.getContent())
                .setSenderId(message.getSenderId())
                .setReceiverId(message.getReceiverId())
                .setPreviousMessageId(message.getPreviousMessageId())
                .setReceiverType(message.getReceiverType())
                .setMessageType(message.getMessageType())
                .setSendTime(TimestampConverter.toTimestamp(message.getSendTime()))
                .setDeviceType(message.getDeviceType())
                .setCreateTime(TimestampConverter.toTimestamp(message.getCreateTime()))
                .setUpdateTime(TimestampConverter.toTimestamp(message.getUpdateTime()))
                .setVersion(message.getVersion());
        return builder.build();
    }
}

