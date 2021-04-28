package com.example.scheduler.api.config.db;

import com.example.scheduler.api.config.PropertiesLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.scheduler.api.domain"},
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
public class DataSourceConfig {

    @Autowired
    PropertiesLoader propertiesLoader;

    @Value("${db.common.tarnsaction.timeout:5}")
    private int transactionTimeout;

    private final String PERSISTENCE_UNIT_NAME = "scheduler";

    @Bean(name = "dataSource")
    public DataSource dataSource() throws Exception {
        HikariConfig hikariConfig = new HikariConfig(propertiesLoader.getHikari());
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);

        dataSource.setLeakDetectionThreshold(transactionTimeout);
        return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        EnableJpaRepositories enableJpaRepositories = this.getClass().getAnnotation(EnableJpaRepositories.class);
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        entityManagerFactoryBean.setJpaProperties(propertiesLoader.getJpa());
        entityManagerFactoryBean.setJpaDialect(new HibernateJpaDialect());

        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactoryBean.setPackagesToScan(enableJpaRepositories.basePackages());
        return entityManagerFactoryBean;
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory.getObject());
        txManager.setDefaultTimeout(transactionTimeout);
        txManager.afterPropertiesSet();
        return txManager;
    }


}
