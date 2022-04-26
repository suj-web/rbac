package com.example.rbac.code.generator;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author suj
 * @create 2022/4/26
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.example.rbac.code.generator.mapper")
public class CodeGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApplication.class, args);
    }
}
