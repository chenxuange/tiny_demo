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
}
