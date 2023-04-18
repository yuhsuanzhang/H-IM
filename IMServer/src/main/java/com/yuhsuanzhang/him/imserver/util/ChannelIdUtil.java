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
//        Properties prop = new Properties();
//        InputStream input = null;
//        try {
//            input = new FileInputStream("application.yml");
//            prop.load(input);
//            serverId = prop.getProperty("im.server.id");
//             使用serverId
//        } catch (IOException ex) {
//             处理异常
//        } finally {
//            if (input != null) {
//                try {
//                    input.close();
//                } catch (IOException e) {
//                     处理异常
//                }
//            }
//        }
        // 加载配置文件
//        ClassPathResource resource = new ClassPathResource("application.yml");
        // 指定文件编码为UTF-8
//        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        // 将yml文件转换成Properties对象
//        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
//        yamlPropertiesFactoryBean.setResources(encodedResource.getResource());
//        Properties properties = yamlPropertiesFactoryBean.getObject();

        // 读取属性
        String serverIdProperty = ConfigUtil.getProperty("im.server.id");
        if (!StringUtils.isEmpty(serverIdProperty)) serverId = serverIdProperty;
        return UUID.randomUUID().toString() + "&" + serverId;
    }
}
