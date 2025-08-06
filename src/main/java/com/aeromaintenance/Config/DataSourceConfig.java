package com.aeromaintenance.Config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("db.mumbai")
    public DataSource mumbaiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("db.delhi")
    public DataSource delhiDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("mumbaiDataSource") DataSource mumbaiDS,
            @Qualifier("delhiDataSource") DataSource delhiDS) {

        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("mumbai", mumbaiDS);
        targetDataSources.put("delhi", delhiDS);

        LocationRoutingDataSource routingDataSource = new LocationRoutingDataSource();
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.setDefaultTargetDataSource(mumbaiDS);
        return routingDataSource;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean mumbaiEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("mumbaiDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.aeromaintenance")
                .persistenceUnit("mumbai")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                        "hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
                ))
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean delhiEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("delhiDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("com.aeromaintenance")
                .persistenceUnit("delhi")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "update",
                        "hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                        "hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
                ))
                .build();
    }


	
    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean routingEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("routingDataSource") DataSource routingDS) {
        return builder
                .dataSource(routingDS)
                .packages("com.aeromaintenance")
                .persistenceUnit("routing")
                .properties(Map.of(
                        "hibernate.hbm2ddl.auto", "none" ,// don't create schema again
                        "hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy",
                        "hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy"
                
                ))
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
    
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
            new HibernateJpaVendorAdapter(),
            Map.of(),
            null
        );
    }

}



/*
 * package com.aeromaintenance.Config;
 * 
 * import java.util.HashMap; import java.util.Map;
 * 
 * import javax.persistence.EntityManagerFactory; import javax.sql.DataSource;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.jdbc.DataSourceBuilder; import
 * org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.Primary; import
 * org.springframework.core.env.Environment; import
 * org.springframework.orm.jpa.JpaTransactionManager; import
 * org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean; import
 * org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter; import
 * org.springframework.transaction.PlatformTransactionManager;
 * 
 * import com.zaxxer.hikari.HikariConfig; import
 * com.zaxxer.hikari.HikariDataSource;
 * 
 * @Configuration public class DataSourceConfig {
 * 
 * @Autowired private Environment env;
 * 
 * @Bean public DataSource dataSource() { Map<Object, Object> dataSources = new
 * HashMap<>();
 * 
 * DataSource mumbaiDataSource = createHikariDataSource(
 * env.getProperty("db.mumbai.url"), env.getProperty("db.mumbai.username"),
 * env.getProperty("db.mumbai.password") );
 * 
 * DataSource delhiDataSource =createHikariDataSource(
 * env.getProperty("db.delhi.url"), env.getProperty("db.delhi.username"),
 * env.getProperty("db.delhi.password") );
 * 
 * dataSources.put("mumbai", mumbaiDataSource); dataSources.put("delhi",
 * delhiDataSource);
 * 
 * LocationRoutingDataSource routingDataSource = new
 * LocationRoutingDataSource();
 * routingDataSource.setDefaultTargetDataSource(mumbaiDataSource); // default
 * routingDataSource.setTargetDataSources(dataSources);
 * routingDataSource.afterPropertiesSet();
 * 
 * return routingDataSource; }
 * 
 * private DataSource createHikariDataSource(String url, String username, String
 * password) { HikariConfig config = new HikariConfig(); config.setJdbcUrl(url);
 * config.setUsername(username); config.setPassword(password);
 * config.setDriverClassName("com.mysql.cj.jdbc.Driver");
 * 
 * config.setMaximumPoolSize(10); config.setPoolName("TenantPool");
 * 
 * return new HikariDataSource(config); }
 * 
 * @Bean public LocalContainerEntityManagerFactoryBean entityManagerFactory(
 * DataSource dataSource, Environment env) {
 * LocalContainerEntityManagerFactoryBean em = new
 * LocalContainerEntityManagerFactoryBean(); em.setDataSource(dataSource);
 * em.setPackagesToScan("com.aeromaintenance"); // Entity package
 * 
 * HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
 * em.setJpaVendorAdapter(vendorAdapter);
 * 
 * Map<String, Object> properties = new HashMap<>();
 * properties.put("hibernate.hbm2ddl.auto",
 * env.getProperty("spring.jpa.hibernate.ddl-auto"));
 * properties.put("hibernate.dialect",
 * env.getProperty("spring.jpa.properties.hibernate.dialect"));
 * properties.put("hibernate.physical_naming_strategy",
 * "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
 * properties.put("hibernate.implicit_naming_strategy",
 * "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
 * em.setJpaPropertyMap(properties); return em; }
 * 
 * @Bean public PlatformTransactionManager transactionManager(
 * EntityManagerFactory emf) { return new JpaTransactionManager(emf); } }
 */