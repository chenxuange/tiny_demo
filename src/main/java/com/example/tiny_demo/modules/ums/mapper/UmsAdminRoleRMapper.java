package com.example.tiny_demo.modules.ums.mapper;


import com.example.tiny_demo.modules.ums.model.UmsAdminRoleR;

import java.util.List;

public interface UmsAdminRoleRMapper {
    /**
     * 根据用户id删除关系记录
     * @param adminId
     */
    void delete(Integer adminId);

    /**
     * 插入用户-角色关系
     * @param adminRoleRList
     */
    void insertBatch(List<UmsAdminRoleR> adminRoleRList);
}
