package com.example.tiny_demo.service;


import com.example.tiny_demo.common.service.RedisService;
import com.example.tiny_demo.modules.ums.service.impl.UmsAdminCacheServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;


    @Test
    public void setTest() {
        String key = "ukraine";
        Integer integer = -1;
        redisService.set(key, integer);
    }

    @Test
    public void getTest() {
        String key = "ukraine";
        Object o = redisService.get(key);
        System.out.println(o);
    }
}
