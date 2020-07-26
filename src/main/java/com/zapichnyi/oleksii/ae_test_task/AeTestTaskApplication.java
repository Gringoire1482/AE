package com.zapichnyi.oleksii.ae_test_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})

public class AeTestTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(AeTestTaskApplication.class, args);
    }

}
