package com.yuhsuanzhang.him.imcommon.enums;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {
    LOGIN(1, "登录"),
    LOGOUT(2, "注销"),
    PING(3, "心跳包"),
    TEXT(4, "文本消息"),
    IMAGE(5, "图片消息"),
    VOICE(6, "语音消息"),
    VIDEO(7, "视频消息"),
    FILE(8, "文件消息"),
    SYSTEM(9, "系统消息");

    private final int code; // 类型代码
    private final String desc; // 类型描述

    MessageTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据代码获取对应的枚举类型
     *
     * @param code 代码
     * @return 对应的枚举类型，如果找不到返回null
     */
    public static MessageTypeEnum getByCode(int code) {
        for (MessageTypeEnum messageType : values()) {
            if (messageType.code == code) {
                return messageType;
            }
        }
        return null;
    }
}

