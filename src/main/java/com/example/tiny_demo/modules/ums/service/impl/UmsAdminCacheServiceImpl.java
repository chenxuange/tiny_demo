package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.service.RedisService;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminRoleRMapper;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsAdminRoleR;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminCacheService;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户缓存service实现类
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    public static final Logger logger = LoggerFactory.getLogger(UmsAdminCacheServiceImpl.class);

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsAdminRoleRMapper adminRoleRMapper;

    @Autowired
    private RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public void setAdmin(UmsAdminDO admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
        logger.debug("setAdmin, admin = {}", admin);
    }

    @Override
    public UmsAdminDO getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        logger.debug("getAdmin, username = {}", username);
        return (UmsAdminDO) redisService.get(key);
    }

    @Override
    public void delAdmin(Integer adminId) {
        UmsAdminDO admin = adminService.get(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.delete(key);
            logger.debug("delAdmin, adminId = {}", adminId);
        }
    }

    @Override
    public void setResourceList(Integer adminId, List<UmsResourceDo> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResourceDo> getResourceList(Integer adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResourceDo>) redisService.get(key);
    }

    @Override
    public void delResourceList(Integer adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.delete(key);
    }

    @Override
    public void delResourceListByRole(Integer roleId) {
        List<UmsAdminRoleR> relationList = adminRoleRMapper.selectByRoleId(roleId);
        if(CollectionUtils.isEmpty(relationList))
            return;
        String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
        redisService.delete(keys);
    }

    @Override
    public void delResourceListByResource(Integer resourceId) {
        // TODO 根据资源id找出关联的用户id
//        List<UmsAdminRoleR> relationList = adminMapper.getAdminIdList(roleId);
//        if(CollectionUtils.isEmpty(relationList))
//            return;
//        String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
//        List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
//        redisService.delete(keys);
    }
}
