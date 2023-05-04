package com.yuhsuanzhang.him.imserver.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.yuhsuanzhang.him.imcommon.enums.DataSourceEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @description: 数据源配置
 * @author: yuxuan.zhang@bitmain.com
 **/
@Slf4j
@Configuration
@EnableConfigurationProperties(DruidDataSourceProperties.class)
@MapperScan(basePackages = "com.yuhsuanzhang.him.imserver.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    @Resource
    private DruidDataSourceProperties druidDataSourceProperties;

    @Bean(name = "databaseMaster")
    @ConfigurationProperties(prefix = "spring.datasource.mysql.master")
    public javax.sql.DataSource databaseMaster() {
        log.info("master datasource");
        return new DruidDataSource();
    }

    @Bean(name = "databaseSlave")
    @ConfigurationProperties(prefix = "spring.datasource.mysql.slave")
    public javax.sql.DataSource databaseSlave() {
        log.info("slave datasource");
        return new DruidDataSource();
    }


    /***
     * @Primary： 相同的bean中，优先使用用@Primary注解的bean.
     * @Qualifier:： 这个注解则指定某个bean有没有资格进行注入。
     */
    @Lazy
    @Bean(name = "dataSourceRouter") // 对应Bean: DataSourceRouter
    public javax.sql.DataSource dataSourceRouter(@Qualifier("databaseMaster") javax.sql.DataSource master,
                                                 @Qualifier("databaseSlave") javax.sql.DataSource slave) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        //配置多数据源
        Map<Object, Object> map = new HashMap<>(5);
        map.put(DataSourceEnum.MASTER.getName(), master);    // key需要跟ThreadLocal中的值对应
        map.put(DataSourceEnum.SLAVE.getName(), slave);
        log.info(" ---------------------- druid properties BEGIN----------------------");
        for (Object name : map.keySet()) {
            log.info("Data source name [{}]", name);
            DruidDataSource source = (DruidDataSource) map.get(name);
            source.setInitialSize(druidDataSourceProperties.getInitialSize());
            source.setMinIdle(druidDataSourceProperties.getMinIdle());
            source.setMaxActive(druidDataSourceProperties.getMaxActive());
            source.setMaxWait(druidDataSourceProperties.getMaxWait());
            source.setPoolPreparedStatements(druidDataSourceProperties.isPoolPreparedStatements());
            source.setMaxPoolPreparedStatementPerConnectionSize(druidDataSourceProperties.getMaxPoolPreparedStatementPerConnectionSize());
            source.setValidationQuery(druidDataSourceProperties.getValidationQuery());
            source.setValidationQueryTimeout(druidDataSourceProperties.getValidationQueryTimeout());
            source.setTestWhileIdle(druidDataSourceProperties.isTestWhileIdle());
            source.setMaxEvictableIdleTimeMillis(druidDataSourceProperties.getMaxEvictableIdleTimeMillis());
            source.setMinEvictableIdleTimeMillis(druidDataSourceProperties.getMinEvictableIdleTimeMillis());
            source.setTimeBetweenEvictionRunsMillis(druidDataSourceProperties.getTimeBetweenEvictionRunsMillis());
            source.setBreakAfterAcquireFailure(druidDataSourceProperties.isBreakAfterAcquireFailure());
            source.setConnectionErrorRetryAttempts(druidDataSourceProperties.getConnectionErrorRetryAttempts());
            source.setLogAbandoned(druidDataSourceProperties.isLogAbandoned());
            source.setRemoveAbandonedTimeoutMillis(druidDataSourceProperties.getRemoveAbandonedTimeoutMillis());
            log.info("DruidDataSource initialSize: {}", source.getInitialSize());
            log.info("DruidDataSource minIdle: {}", source.getMinIdle());
            log.info("DruidDataSource maxActive: {}", source.getMaxActive());
            log.info("DruidDataSource maxWait: {}", source.getMaxWait());
            log.info("DruidDataSource poolPreparedStatements: {}", source.isPoolPreparedStatements());
            log.info("DruidDataSource maxPoolPreparedStatementPerConnectionSize: {}", source.getMaxPoolPreparedStatementPerConnectionSize());
            log.info("DruidDataSource validationQuery: {}", source.getValidationQuery());
            log.info("DruidDataSource validationQueryTimeout: {}", source.getValidationQueryTimeout());
            log.info("DruidDataSource testWhileIdle: {}", source.isTestWhileIdle());
            log.info("DruidDataSource maxEvictableIdleTimeMillis: {}", source.getMaxEvictableIdleTimeMillis());
            log.info("DruidDataSource minEvictableIdleTimeMillis: {}", source.getMinEvictableIdleTimeMillis());
            log.info("DruidDataSource timeBetweenEvictionRunsMillis: {}", source.getTimeBetweenEvictionRunsMillis());
            log.info("DruidDataSource breakAfterAcquireFailure: {}", source.isBreakAfterAcquireFailure());
            log.info("DruidDataSource connectionErrorRetryAttempts: {}", source.getConnectionErrorRetryAttempts());
            log.info("DruidDataSource logAbandoned: {}", source.isLogAbandoned());
            log.info("DruidDataSource removeAbandonedTimeoutMillis: {}", source.getRemoveAbandonedTimeoutMillis());

        }
        log.info(" ---------------------- druid properties END----------------------");
        // master 作为默认数据源
        dataSourceRouter.setDefaultTargetDataSource(master);
        dataSourceRouter.setTargetDataSources(map);
        return dataSourceRouter;
    }


    // 注入动态数据源 DataSourceTransactionManager 用于事务管理(事务回滚只针对同一个数据源)
    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(@Qualifier("dataSourceRouter") javax.sql.DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /*由于Spring容器中现在有4个数据源，所以我们需要为事务管理器和MyBatis手动指定一个明确的数据源。*/
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceRouter") javax.sql.DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        //我采取的是注解式sql，如果加上扫描，但包下无mapper.xml会报错
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/yuhsuanzhang/him/imserver/mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.yuhsuanzhang.him.imcommon.entity");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置 SqlSessionTemplate
     *
     * @param sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
