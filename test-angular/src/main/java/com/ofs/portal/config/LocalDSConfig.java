package com.ofs.portal.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.ofs.portal.dao", sqlSessionFactoryRef = "sqlSessionFactory")
public class LocalDSConfig {

    private static final Logger logger = LoggerFactory.getLogger(LocalDSConfig.class);

    @Value("${bonecp.url}")
    private String jdbcUrl;

    @Value("${bonecp.username}")
    private String jdbcUsername;

    @Value("${bonecp.password}")
    private String jdbcPassword;

    @Value("${bonecp.driverClass}")
    private String driverClass;

    @Value("${bonecp.idleMaxAgeInMinutes}")
    private String idleMaxAgeInMinutes;

    @Value("${bonecp.idleConnectionTestPeriodInMinutes}")
    private String idleConnectionTestPeriodInMinutes;

    @Value("${bonecp.maxConnectionsPerPartition}")
    private String maxConnectionsPerPartition;

    @Value("${bonecp.minConnectionsPerPartition}")
    private String minConnectionsPerPartition;

    @Value("${bonecp.partitionCount}")
    private String partitionCount;

    @Value("${bonecp.acquireIncrement}")
    private String acquireIncrement;

    @Value("${bonecp.statementsCacheSize}")
    private String statementsCacheSize;

    @Value("${ofs.srv.url}")
    private String ofsSrvUrl;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {

        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        dataSource.setIdleConnectionTestPeriodInMinutes(Long.valueOf((idleConnectionTestPeriodInMinutes)).longValue());
        dataSource.setIdleMaxAgeInMinutes(Long.parseLong(idleMaxAgeInMinutes));
        dataSource.setMaxConnectionsPerPartition(Integer.parseInt(maxConnectionsPerPartition));
        dataSource.setMinConnectionsPerPartition(Integer.parseInt(minConnectionsPerPartition));
        dataSource.setPartitionCount(Integer.parseInt(partitionCount));
        dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
        dataSource.setStatementsCacheSize(Integer.parseInt(statementsCacheSize));
        logger.info("Data source intialized ----");
        return dataSource;
    }

    @Bean
    @Qualifier("localDSTxMgr")
    public DataSourceTransactionManager transactionManager() throws ClassNotFoundException {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setTypeAliasesPackage("com.ofs.portal.model");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/ofs/portal/dao/*.xml"));
        return (SqlSessionFactory) sessionFactoryBean.getObject();
    }

}