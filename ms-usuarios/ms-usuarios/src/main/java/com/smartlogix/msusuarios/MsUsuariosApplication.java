package com.smartlogix.msusuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartlogix.msusuarios.repository")
public class MsUsuariosApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsUsuariosApplication.class, args);
    }
}