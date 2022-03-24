package com.example.tiny_demo.common.service.impl;

import com.example.tiny_demo.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 通用业务类：
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

    // redis设置缓存，默认时间单位为秒
    @Override
    public void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public Long delete(List<String> keys) {
        return redisTemplate.delete(keys);
    }
}
