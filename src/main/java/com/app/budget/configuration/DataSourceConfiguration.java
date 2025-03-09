package com.app.budget.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties("app.datasource")
    @Profile("dev")
    @Qualifier("datasource")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("spring.datasource")
    @Qualifier("test")
    public HikariDataSource repositoryTestDatasource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    @ConfigurationProperties("app-test.datasource")
    @Profile("test-containers")
    @Qualifier("test-datasource")
    public HikariDataSource testDatasource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @Profile("test-containers")
    @ConfigurationProperties("app-test.datasource")
    public NamedParameterJdbcTemplate getJdbcTemplate(
            @Qualifier("test-datasource")
            HikariDataSource dataSource
    ) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile("test")
    @ConfigurationProperties("spring.datasource")
    public NamedParameterJdbcTemplate getH2JdbcTemplate(
            @Qualifier("test")
            HikariDataSource dataSource
    ) {
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
