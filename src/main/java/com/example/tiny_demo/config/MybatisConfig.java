package com.example.tiny_demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/*
* mybatis配置，开启mapper扫描，解析mapper接口
* */
@Configuration
@MapperScan("com.example.tiny_demo.modules.ums.mapper")
public class MybatisConfig {
}
