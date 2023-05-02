package com.yuhsuanzhang.him.imserver.config.datasource;

import com.yuhsuanzhang.him.imcommon.annotation.DataSourceAnnotation;
import com.yuhsuanzhang.him.imcommon.enums.DataSourceEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @description: AOP根据注解给上下文赋值
 * @author: yuxuan.zhang@bitmain.com
 **/
@Aspect
@Order(1)    // 数据源的切换要在数据库事务之前, 设置AOP执行顺序(需要在事务之前，否则事务只发生在默认库中, 数值越小等级越高)
@Component
public class DataSourceAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 切点, 注意这里是在service层
    @Pointcut(
//            "!@annotation(com.x.xmall.annotation.Master) " +
            "execution(* com.yuhsuanzhang.him.*.service..*.select*(..)) " +
                    "|| execution(* com.yuhsuanzhang.him.*.service..*.get*(..))" +
                    "|| execution(* com.yuhsuanzhang.him.*.service..*.find*(..))")
    public void readPointcut() {

    }

    @Pointcut(
//            "@annotation(com.x.xmall.annotation.Master) " +
            "execution(public * com.yuhsuanzhang.him.*.service..*.save*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.insert*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.update*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.edit*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.delete*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.del*(..)) " +
                    "|| execution(public * com.yuhsuanzhang.him.*.service..*.remove*(..)) ")
    public void writePointcut() {
    }

    @Before("readPointcut()")
    public void read(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSourceAnnotation.class)) {
                DataSourceAnnotation data = m.getAnnotation(DataSourceAnnotation.class);
                DataSourceContextHolder.putDataSource(data.value().getName());
                log.info("-----------Switch data source, context is ready for assignment-----:{}", data.value().getName());
                log.info("-----------Switch the data source, the actual assignment of the data source context-----:{}", DataSourceContextHolder.getCurrentDataSource());
                log.info("DataSourceAspect before read with annotation");
            } else {
                DataSourceContextHolder.putDataSource(DataSourceEnum.SLAVE.getName());
                log.info("DataSourceAspect before read with normal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Before("writePointcut()")
    public void write(JoinPoint point) {
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?> classz = target.getClass();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            Method m = classz.getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSourceAnnotation.class)) {
                DataSourceAnnotation data = m.getAnnotation(DataSourceAnnotation.class);
                DataSourceContextHolder.putDataSource(data.value().getName());
                log.info("-----------Switch data source, context is ready for assignment-----:{}", data.value().getName());
                log.info("-----------Switch the data source, the actual assignment of the data source context-----:{}", DataSourceContextHolder.getCurrentDataSource());
                log.info("DataSourceAspect before read with annotation");
            } else {
                DataSourceContextHolder.putDataSource(DataSourceEnum.MASTER.getName());
                log.info("DataSourceAspect before read with normal");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 切面结束, 重置线程变量
    @After("readPointcut()")
    public void afterRead(JoinPoint joinPoint) {
        DataSourceContextHolder.removeCurrentDataSource();
        log.info("reset datasource: Restore DataSource to [{}] in Method [{}] after read", DataSourceContextHolder.getCurrentDataSource(), joinPoint.getSignature());
    }

    // 切面结束, 重置线程变量
    @After("writePointcut()")
    public void afterWrite(JoinPoint joinPoint) {
        DataSourceContextHolder.removeCurrentDataSource();
        log.info("reset datasource: Restore DataSource to [{}] in Method [{}] after write", DataSourceContextHolder.getCurrentDataSource(), joinPoint.getSignature());
    }
}
