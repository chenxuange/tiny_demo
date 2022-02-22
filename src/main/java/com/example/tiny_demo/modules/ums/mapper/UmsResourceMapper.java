package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsResourceDo;

import java.util.List;

public interface UmsResourceMapper {
    /**
     * 根据条件查询资源
     * @param umsResourceDo
     * @return
     */
    List<UmsResourceDo> selectList(UmsResourceDo umsResourceDo);

    /**
     * 根据资源id查找资源
     * @param id
     * @return
     */
    List<UmsResourceDo> selectById(Integer id);

    /**
     * 插入资源
     * @param resourceDo
     */
    void insert(UmsResourceDo resourceDo);

    /**
     * 删除资源
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新资源
     * @param umsResourceDo
     */
    void updateById(UmsResourceDo umsResourceDo);

    /**
     * 根据角色id获取关联资源
     * @param roleId
     */
    List<UmsResourceDo> getResourceListByRoleId(Integer roleId);

    /**
     * 批量查询资源
     * @param resourceIds
     */
    List<UmsResourceDo> selectByIdBatch(List<Integer> resourceIds);

    /**
     * 根据用户id获取所有关联资源
     * 当用户角色关系表中没找到用户id时，表示该用户还没有角色，自然也就没有资源列表了
     * @param adminId
     */
    List<UmsResourceDo> getResourceList(Integer adminId);
}
