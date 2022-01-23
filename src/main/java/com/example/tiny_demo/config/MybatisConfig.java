package com.example.tiny_demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.tiny_demo.mapper")
public class MybatisConfig {
}
