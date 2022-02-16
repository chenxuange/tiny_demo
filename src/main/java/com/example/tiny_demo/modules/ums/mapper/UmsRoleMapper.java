package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsRoleDo;

import java.util.List;

public interface UmsRoleMapper {
    /**
     * 根据用户id、用户角色关系表、角色表，关联查出指定用户id的所有角色
     * @param adminId 用户id
     * @return
     */
    List<UmsRoleDo> getRoleList(Integer adminId);


    /**
     * 根据条件查询角色
     * @param umsRoleDo
     */
    List<UmsRoleDo> selectList(UmsRoleDo umsRoleDo);

    /**
     *新增用户角色
     * @param roleDo
     */
    void insert(UmsRoleDo roleDo);

    /**
     * 更新用户角色
     * @param roleDo
     */
    void updateById(UmsRoleDo roleDo);

    /**
     * 批量删除角色
     * @param ids
     */
    void deleteBatchIds(List<Integer> ids);

    /**
     * 根据角色id查询角色
     * @param id 角色id
     */
    List<UmsRoleDo> selectById(Integer id);
}
