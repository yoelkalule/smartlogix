package com.smartlogix.msenvios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsEnviosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsEnviosApplication.class, args);
    }

}