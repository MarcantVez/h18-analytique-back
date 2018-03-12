package com.squidsquads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.sql.DataSourceDefinition;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.squidsquads.config", "com.squidsquads.controller", "com.squidsquads.service", "com.squidsquads.utils"})
@EntityScan("com.squidsquads.model")
@EnableJpaRepositories("com.squidsquads.repository")
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}