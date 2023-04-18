package com.yuhsuanzhang.him.imserver.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author yuxuan.zhang
 * @Description
 */
@Slf4j
public class ConfigUtil {
    private static final Map<String, String> CONFIG_MAP = new HashMap<>();

    static {
        try {
            // 加载配置文件
            ClassPathResource resource = new ClassPathResource("application.yml");
            // 指定文件编码为UTF-8
            EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
            // 将yml文件转换成Properties对象
            YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
            yamlPropertiesFactoryBean.setResources(encodedResource.getResource());
            Properties properties = yamlPropertiesFactoryBean.getObject();
            // 将配置信息保存在静态变量中
            if (properties != null && !properties.isEmpty()) {
                for (String name : properties.stringPropertyNames()) {
                    CONFIG_MAP.put(name, properties.getProperty(name));
                }
            }
        } catch (Exception e) {
            // 处理异常
            log.error("ConfigUtil set config map error:\n", e);
        }
    }

    /**
     * @description: Read configuration file information by configuration file field name
     * @author: yuxuan.zhang@bitmain.com
     * @Date 12:41 下午 2023/4/18
     * @Param [name]
     * @return java.lang.String
     **/
    public static String getProperty(String name) {
        return CONFIG_MAP.get(name);
    }
}
