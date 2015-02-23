package com.ofs.srv.config;

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
@MapperScan(basePackages = "com.ofs.srv.dao.hrm", sqlSessionFactoryRef = "sqlSessionFactoryHRM")
public class HRMDSConfig {

    private static final Logger logger = LoggerFactory.getLogger(HRMDSConfig.class);

    @Value("${hrmds.url}")
    private String jdbcUrl;

    @Value("${hrmds.username}")
    private String jdbcUsername;

    @Value("${hrmds.password}")
    private String jdbcPassword;

    @Value("${hrmds.driverClass}")
    private String driverClass;

    @Value("${hrmds.idleMaxAgeInMinutes}")
    private String idleMaxAgeInMinutes;

    @Value("${hrmds.idleConnectionTestPeriodInMinutes}")
    private String idleConnectionTestPeriodInMinutes;

    @Value("${hrmds.maxConnectionsPerPartition}")
    private String maxConnectionsPerPartition;

    @Value("${hrmds.minConnectionsPerPartition}")
    private String minConnectionsPerPartition;

    @Value("${hrmds.partitionCount}")
    private String partitionCount;

    @Value("${hrmds.acquireIncrement}")
    private String acquireIncrement;

    @Value("${hrmds.statementsCacheSize}")
    private String statementsCacheSize;

    @Bean(destroyMethod = "close")
    public DataSource dataSourceHRM() {

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
        logger.info("HRM data source intialized ----");
        return dataSource;
    }

    @Bean
    @Qualifier("hrmDSTxMgr")
    public DataSourceTransactionManager transactionManagerHRM() throws ClassNotFoundException {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSourceHRM());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryHRM() throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSourceHRM());
        sessionFactoryBean.setTypeAliasesPackage("com.ofs.srv.model");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/ofs/srv/dao/hrm/*.xml"));
        return (SqlSessionFactory) sessionFactoryBean.getObject();
    }

}