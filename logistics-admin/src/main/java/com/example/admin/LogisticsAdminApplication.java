package com.example.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication(scanBasePackages = {"com.example.admin","com.example.system"})
public class LogisticsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogisticsAdminApplication.class, args);
    }

}
