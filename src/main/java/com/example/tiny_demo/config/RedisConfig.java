package com.example.tiny_demo.config;

import com.example.tiny_demo.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
/*
* 自定义缓存配置
* */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
