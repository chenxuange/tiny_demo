package com.example.tiny_demo.common.service;

import java.util.List;

/**
 * 封装底层redisTemplate操作
 */
public interface RedisService {

    /**
     * 设置属性
     * @param key
     * @param value
     * @param time 毫秒
     */
    void set(String key, Object value, long time);

    /**
     * 获取属性
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 删除属性
     * @param key
     * @return
     */
    Boolean delete(String key);

    /**
     * 批量删除属性
     * @param keys
     * @return
     */
    Long delete(List<String> keys);
}
