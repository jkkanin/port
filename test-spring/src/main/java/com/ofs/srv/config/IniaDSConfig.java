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
@MapperScan(basePackages = "com.ofs.srv.dao.inia", sqlSessionFactoryRef = "sqlSessionFactoryInia")
public class IniaDSConfig {

    private static final Logger logger = LoggerFactory.getLogger(IniaDSConfig.class);

    @Value("${iniads.url}")
    private String jdbcUrl;

    @Value("${iniads.username}")
    private String jdbcUsername;

    @Value("${iniads.password}")
    private String jdbcPassword;

    @Value("${iniads.driverClass}")
    private String driverClass;

    @Value("${iniads.idleMaxAgeInMinutes}")
    private String idleMaxAgeInMinutes;

    @Value("${iniads.idleConnectionTestPeriodInMinutes}")
    private String idleConnectionTestPeriodInMinutes;

    @Value("${iniads.maxConnectionsPerPartition}")
    private String maxConnectionsPerPartition;

    @Value("${iniads.minConnectionsPerPartition}")
    private String minConnectionsPerPartition;

    @Value("${iniads.partitionCount}")
    private String partitionCount;

    @Value("${iniads.acquireIncrement}")
    private String acquireIncrement;

    @Value("${iniads.statementsCacheSize}")
    private String statementsCacheSize;

    @Bean(destroyMethod = "close")
    public DataSource dataSourceInia() {

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
        logger.info("Inia data source intialized ----");
        return dataSource;
    }

    @Bean
    @Qualifier("iniaDSTxMgr")
    public DataSourceTransactionManager transactionManagerInia() throws ClassNotFoundException {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSourceInia());
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryInia() throws Exception {

        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSourceInia());
        sessionFactoryBean.setTypeAliasesPackage("com.ofs.srv.model");
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/ofs/srv/dao/inia/*.xml"));
        return (SqlSessionFactory) sessionFactoryBean.getObject();
    }

}