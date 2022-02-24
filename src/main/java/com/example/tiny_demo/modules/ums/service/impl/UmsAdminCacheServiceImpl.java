package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.service.RedisService;
import com.example.tiny_demo.modules.ums.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台用户缓存service实现类
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private RedisService redisService;
}
