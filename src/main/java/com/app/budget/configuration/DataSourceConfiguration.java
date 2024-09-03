package com.app.budget.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties("app.datasource")
    @Profile("!test")
    public HikariDataSource createDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("app.datasource")
    @Profile("test")
    public HikariDataSource testDatasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }
}
