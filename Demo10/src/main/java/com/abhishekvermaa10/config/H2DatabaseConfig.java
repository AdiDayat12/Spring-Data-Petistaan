package com.abhishekvermaa10.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author abhishekvermaa10
 */
@EnableJpaRepositories(basePackages = "com.abhishekvermaa10.h2.repository", entityManagerFactoryRef = "h2EntityManagerFactory", transactionManagerRef = "h2TransactionManager")
@Configuration
public class H2DatabaseConfig {

	@Value("${spring.h2.jpa.properties.hibernate.dialect}")
	private String hibernateDialect;
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hibernateDdl;
	@Value("${spring.jpa.show-sql}")
	private String hibernateShowSql;

	@Bean
	PlatformTransactionManager h2TransactionManager() {
		final JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(h2EntityManagerFactory().getObject());
		return jpaTransactionManager;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean h2EntityManagerFactory() {
		final LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		localContainerEntityManagerFactoryBean.setDataSource(h2DataSource());
		localContainerEntityManagerFactoryBean.setPackagesToScan("com.abhishekvermaa10.h2.entity");
		final HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		final HashMap<String, Object> propertiesMap = new HashMap<>();
		propertiesMap.put("hibernate.dialect", hibernateDialect);
		propertiesMap.put("hibernate.hbm2ddl.auto", hibernateDdl);
		propertiesMap.put("hibernate.show_sql", hibernateShowSql);
		localContainerEntityManagerFactoryBean.setJpaPropertyMap(propertiesMap);
		return localContainerEntityManagerFactoryBean;
	}

	@ConfigurationProperties(prefix = "spring.h2.datasource")
	@Bean
	DataSource h2DataSource() {
		return DataSourceBuilder.create().build();
	}

}
