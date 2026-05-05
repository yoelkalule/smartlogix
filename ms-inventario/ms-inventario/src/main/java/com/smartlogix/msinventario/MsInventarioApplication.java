package com.smartlogix.msinventario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MsInventarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsInventarioApplication.class, args);
    }

}