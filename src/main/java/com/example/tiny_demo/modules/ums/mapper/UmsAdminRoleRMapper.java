package com.example.tiny_demo.modules.ums.mapper;


import com.example.tiny_demo.modules.ums.model.UmsAdminRoleR;

import java.util.List;

public interface UmsAdminRoleRMapper {
    /**
     * 根据用户id删除关系
     * @param adminId
     */
    void delete(Integer adminId);

    /**
     * 插入用户-角色关系
     * @param adminRoleRList
     */
    void insertBatch(List<UmsAdminRoleR> adminRoleRList);

    /**
     * 根据角色id查询所有关系记录
     * @param roleId
     * @return
     */
    List<UmsAdminRoleR> selectByRoleId(Integer roleId);

    /**
     * 批量查询关系
     * @param roleIds
     */
    List<UmsAdminRoleR> selectByRoleIds(List<Integer> roleIds);
}
