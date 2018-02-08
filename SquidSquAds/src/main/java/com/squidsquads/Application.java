package com.squidsquads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author: Dulce Cayetano
 * @Date_Of_Creation: 2018-01-16
 * @Last_modified_by:
 * @Date_of_last_modification:
 **/
@ComponentScan({"com.squidsquads.controller", "com.squidsquads.service"})
@EnableJpaRepositories("com.squidsquads.repository")
@EntityScan("com.squidsquads.model")
@Configuration
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
