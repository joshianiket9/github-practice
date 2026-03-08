package com.cardworks.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("test")
public class TestConfiguration {

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> testPortCustomizer() {
		return factory ->factory.setPort(8282);
	}
	
	@Bean
	public DataSource getDataSource() {
		
		return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/cardworks")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username("root")
                .password("root")
                .build();
		
		
		
		//DriverManagerDataSource dataSource = new DriverManagerDataSource();
		//dataSource.setDriverClassName("");
		//dataSource.setUrl("");
		//dataSource.setUsername("root");
		//dataSource.setPassword("root");
		//return dataSource;
	}

	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("com.cardworks");
	
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");

        emf.setJpaProperties(properties);
        return emf;
	}
	
	@Bean
    public PlatformTransactionManager transactionManager(
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory.getObject());
    }
}

/*

spring.datasource.url=jdbc:mysql://localhost:3306/cardworks?useSSL=false&serverTimezone=Asia/Kolkata&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true



*/