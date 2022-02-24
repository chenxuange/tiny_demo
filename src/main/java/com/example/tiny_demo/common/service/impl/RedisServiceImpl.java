package com.example.tiny_demo.common.service.impl;

import com.example.tiny_demo.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * RedisService实现类
 * 自动注入底层的RedisTemplate操作模板
 * 注意：
 * 1. StringRedisTemplate可以直接注入
 * 2. 而RedisTemplate则需要先配置
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
