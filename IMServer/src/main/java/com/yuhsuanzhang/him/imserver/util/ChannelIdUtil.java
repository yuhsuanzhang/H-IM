package com.yuhsuanzhang.him.imserver.util;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.StringUtils;

import java.util.Properties;
import java.util.UUID;

/**
 * @Author yuxuan.zhang
 * @Description
 */
public class ChannelIdUtil {

    public static String get() {
        String serverId = "default";
        // 读取属性
        String serverIdProperty = ConfigUtil.getProperty("im.server.id");
        if (!StringUtils.isEmpty(serverIdProperty)) serverId = serverIdProperty;
        return UUID.randomUUID().toString() + "&" + serverId;
    }
}
