package com.example.tiny_demo.common.service;

/**
 * 封装底层redisTemplate操作
 */
public interface RedisService {

    /**
     * 设置属性
     * @param key
     * @param value
     */
    void set(String key, Object value);

    /**
     * 获取属性
     * @param key
     * @return
     */
    Object get(String key);
}
