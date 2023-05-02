package com.yuhsuanzhang.him.imserver.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.yuhsuanzhang.him.imcommon.enums.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 数据源配置
 * @author: yuxuan.zhang@bitmain.com
 **/
@Configuration
@MapperScan(basePackages = "com.portal.oa.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public final static String masterTransactionManager = "masterTransactionManager";

    public final static String slaveTransactionManager = "slaveTransactionManager";

    /***
     * 注意这里用的 Druid 连接池
     */
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
    public javax.sql.DataSource dataSourceRouter(@Qualifier("databaseMaster") javax.sql.DataSource master, @Qualifier("databaseSlave") javax.sql.DataSource slave) {
        DataSourceRouter dataSourceRouter = new DataSourceRouter();
        log.info(" ---------------------- 德鲁伊配置信息 BEGIN----------------------");
        DruidDataSource druidDataSourceMaster = (DruidDataSource) master;
        DruidDataSource druidDataSourceSlave = (DruidDataSource) slave;
        log.info("master: ");
        log.info("检测连接是否有效的sql: " + druidDataSourceMaster.getValidationQuery());
        log.info("最小空闲连接池数量: " + druidDataSourceMaster.getMinIdle());
        log.info("removeAbandoned功能: " + druidDataSourceMaster.removeAbandoned());
        log.info("超过时间限制时间(单位秒): " + druidDataSourceMaster.getRemoveAbandonedTimeout());
        log.info("slave: ");
        log.info("检测连接是否有效的sql: " + druidDataSourceSlave.getValidationQuery());
        log.info("最小空闲连接池数量: " + druidDataSourceSlave.getMinIdle());
        log.info("removeAbandoned功能: " + druidDataSourceSlave.removeAbandoned());
        log.info("超过时间限制时间(单位秒): " + druidDataSourceSlave.getRemoveAbandonedTimeout());
        log.info(" ---------------------- 德鲁伊配置信息 END----------------------");

        //配置多数据源
        Map<Object, Object> map = new HashMap<>(5);
        map.put(DataSourceEnum.MASTER.getName(), master);    // key需要跟ThreadLocal中的值对应
        map.put(DataSourceEnum.SLAVE.getName(), slave);
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
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*::mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.portal.oa.entity");
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 配置 SqlSessionTemplate
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
