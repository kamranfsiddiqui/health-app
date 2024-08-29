package com.kfs.health_app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "datasources")
public class DatasourcesConfig {
    Map<String, String> health = new HashMap<>();

    public Map<String, String> getHealth() {
        return health;
    }

    @Bean("healthDatasource")
    DataSource healthDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setUrl(health.get("url"));
        driverManagerDataSource.setUsername(health.get("user"));
        driverManagerDataSource.setPassword(health.get("password"));
        driverManagerDataSource.setDriverClassName(health.get("driver"));
        return driverManagerDataSource;
    }

    @Bean
    NamedParameterJdbcTemplate healthJdbcTemplate(@Qualifier("healthDatasource") DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
