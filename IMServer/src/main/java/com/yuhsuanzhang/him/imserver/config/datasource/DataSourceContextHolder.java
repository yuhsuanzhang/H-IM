package com.yuhsuanzhang.him.imserver.config.datasource;

/**
 * @description: 动态数据源的上下文 ThreadLocal
 * @author: yuxuan.zhang@bitmain.com
 **/
public class DataSourceContextHolder {
    private final static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void putDataSource(String name) {
        threadLocal.set(name);
    }

    public static String getCurrentDataSource() {
        return threadLocal.get();
    }

    public static void removeCurrentDataSource() {
        threadLocal.remove();
    }

}