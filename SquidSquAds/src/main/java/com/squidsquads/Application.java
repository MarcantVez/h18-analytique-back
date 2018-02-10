package com.squidsquads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"com.squidsquads.config", "com.squidsquads.controller", "com.squidsquads.service", "com.squidsquads.utils"})
@EnableJpaRepositories("com.squidsquads.repository")
@EntityScan("com.squidsquads.model")
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        applicationContext = SpringApplication.run(Application.class, args);
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }
}
