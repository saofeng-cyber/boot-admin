package com.saofeng.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@MapperScan("com.saofeng.admin.mapper")
public class SaofengAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaofengAdminApplication.class, args);
    }
    @GetMapping("/")
    public String getStatus(){
        return "1341521";
    }
}