package com.smartlogix.msenvios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartlogix.msenvios.repository")
public class MsEnviosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsEnviosApplication.class, args);
    }
}