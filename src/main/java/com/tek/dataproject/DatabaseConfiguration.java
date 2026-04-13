package com.tek.dataproject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

//Manual configurations from the application properties are required since the program starts using javaFX
@Configuration
public class DatabaseConfiguration{


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String username;

    //This has spring take the returned datasource and stores it as a bean to be used throughout the program
    @Bean
    public DataSource createDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    //The Jdbc bean is used to take away most of the boilerplate code like handling exceptions that come from the database request
    //I help to make the management and the connection of sending the queries easier
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(createDataSource());
    }
}
