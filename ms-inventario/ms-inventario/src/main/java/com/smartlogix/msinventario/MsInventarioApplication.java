package com.smartlogix.msinventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smartlogix.msinventario.repository")
public class MsInventarioApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsInventarioApplication.class, args);
    }
}