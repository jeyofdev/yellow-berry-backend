package com.jeyofdev.yellow_berry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfig {
    @Value("${DB_USER}")
    private String dbUser;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    @Value("${DB_NAME}")
    private String dbName;

    @Bean
    public String getDbUser() {
        return dbUser;
    }

    @Bean
    public String getDbPassword() {
        return dbPassword;
    }

    @Bean
    public String getDbName() {
        return dbName;
    }
}
