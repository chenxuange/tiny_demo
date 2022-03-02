package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.service.RedisService;
import com.example.tiny_demo.modules.ums.mapper.UmsAdminMapper;
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

import java.util.ArrayList;
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
    private UmsAdminMapper adminMapper;

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
        logger.debug("setAdmin, {}", admin);
        // 根据用户名构造用户缓存键
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public UmsAdminDO getAdmin(String username) {
        logger.debug("getAdmin, username = {}", username);
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
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
        logger.debug("setResourceList, {}", adminId);
        // 根据用户id构造资源缓存键
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResourceDo> getResourceList(Integer adminId) {
        logger.debug("getResourceList, adminId = {}", adminId);
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResourceDo>) redisService.get(key);
    }

    @Override
    public void delResourceList(Integer adminId) {
        logger.debug("delResourceList, {}", adminId);
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.delete(key);
        logger.debug("key, {}", key);
    }

    @Override
    public void delResourceListByRole(Integer roleId) {
//        logger.debug("delResourceListByRole, {}", roleId);
        // 先要去查，根据查的结果构造redis键
        List<Integer> roleIds = new ArrayList<>();
        roleIds.add(roleId);
        delResourceListByRoles(roleIds);
    }

    @Override
    public void delResourceListByRoles(List<Integer> roleIds) {
        logger.debug("delResourceListByRoles, {}", roleIds);
        List<UmsAdminRoleR> relationList = adminRoleRMapper.selectByRoleIds(roleIds);
        if(CollectionUtils.isEmpty(relationList)) {
            return;
        }
        String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
        redisService.delete(keys);
        logger.debug("keys, {}", keys);
    }

    @Override
    public void delResourceListByResource(Integer resourceId) {
        logger.debug("delResourceListByResource, {}", resourceId);
        // 先要去查，根据查的结果构造redis键
        // 根据资源id找出关联的用户id
        List<Integer> adminIdList = adminMapper.getAdminIdList(resourceId);
        if(CollectionUtils.isEmpty(adminIdList)) {
            return;
        }
        String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
        List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
        redisService.delete(keys);
        logger.debug("keys, {}", keys);
    }
}
