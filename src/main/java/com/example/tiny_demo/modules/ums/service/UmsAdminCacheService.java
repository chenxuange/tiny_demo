package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;

import java.util.List;

/**
 * 后台用户缓存service
 */
public interface UmsAdminCacheService {

    /**
     * 设置后台用户缓存
     * @param admin
     */
    void setAdmin(UmsAdminDO admin);

    /**
     * 获取后台用户缓存
     * @param username
     * @return
     */
    UmsAdminDO getAdmin(String username);


    /**
     * 删除后台用户缓存
     * @param adminId
     */
    void delAdmin(Integer adminId);

    /**
     * 添加后台用户资源缓存列表
     * @param adminId
     * @param resourceList
     */
    void setResourceList(Integer adminId, List<UmsResourceDo> resourceList);

    /**
     * 根据用户id获取后台资源缓存列表
     * @param adminId
     * @return
     */
    List<UmsResourceDo> getResourceList(Integer adminId);

    /**
     * 根据用户id移除后台资源缓存
     * 涉及用户资源列表改变的更新操作或者删除操作。
     * 如 删除用户、更新了用户角色关系(为用户分配角色)
     * @param adminId
     */
    void delResourceList(Integer adminId);

    /**
     * 根据角色id移除后台资源缓存
     * 涉及用户资源列表改变的更新操作或者删除操作
     * 如 为角色分配资源、更新了角色状态、删除了角色
     * @param roleId
     */
    void delResourceListByRole(Integer roleId);

    /**
     * 根据资源id移除后台资源缓存
     * 涉及用户资源列表改变的更新操作或者删除操作
     * 如 更新了资源、删除了资源
     * @param resourceId
     */
    void delResourceListByResource(Integer resourceId);
}
