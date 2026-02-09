package com.kh.finalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// ▼▼▼ [수정 전: exclude가 있어서 보안 기능이 꺼져있었음] ▼▼▼
// @SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})

// ▼▼▼ [수정 후: exclude 제거] ▼▼▼
@SpringBootApplication
@ComponentScan(basePackages = { "com.kh.finalproject" })
public class FinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

}
