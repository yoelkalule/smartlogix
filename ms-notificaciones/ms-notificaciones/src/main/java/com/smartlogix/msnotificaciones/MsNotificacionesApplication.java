package com.smartlogix.msnotificaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartlogix.msnotificaciones.repository")
public class MsNotificacionesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsNotificacionesApplication.class, args);
    }
}