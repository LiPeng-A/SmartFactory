package com.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.smart.mapper")
public class SmartApplication {



    public static void main(String[] args) {
        SpringApplication.run(SmartApplication.class);
    }
}
