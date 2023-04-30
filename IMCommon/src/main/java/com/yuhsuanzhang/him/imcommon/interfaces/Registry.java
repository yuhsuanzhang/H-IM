package com.yuhsuanzhang.him.imcommon.interfaces;

import java.util.List;

public interface Registry {
    // 注册服务
    void register(String serviceName, String serviceAddress) throws Exception;

    // 发现服务
    List<String> discover(String serviceName) throws Exception;

    //注销服务
    void unregister(String serviceName, String serviceAddress) throws Exception;

    //关闭zookeeper
    void close() throws Exception;
}
