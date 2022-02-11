package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsRoleDo;

import java.util.List;

public interface UmsRoleMapper {
    /**
     * 根据用户id、用户角色关系表、角色表，关联查出指定用户id的所有角色
     * @param adminId
     * @return
     */
    List<UmsRoleDo> getRoleList(Integer adminId);
}
