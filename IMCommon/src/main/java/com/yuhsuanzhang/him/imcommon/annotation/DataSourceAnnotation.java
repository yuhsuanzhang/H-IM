package com.yuhsuanzhang.him.imcommon.annotation;

import com.yuhsuanzhang.him.imcommon.enums.DataSourceEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 数据源选择--自定义注解
 * @author: yuxuan.zhang@bitmain.com
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceAnnotation {

    DataSourceEnum value() default DataSourceEnum.MASTER;    // 默认主表master

}
