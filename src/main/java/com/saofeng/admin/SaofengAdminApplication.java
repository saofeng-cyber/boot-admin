package com.saofeng.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.saofeng.admin.mapper")
@EnableCaching
public class SaofengAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaofengAdminApplication.class, args);
    }
}
