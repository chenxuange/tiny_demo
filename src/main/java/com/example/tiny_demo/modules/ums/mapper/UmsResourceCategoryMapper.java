package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsResourceCategoryDo;

import java.util.List;

public interface UmsResourceCategoryMapper {

    /**
     * 根据条件查询资源分类
     * @param resourceCategoryDo
     * @return
     */
    List<UmsResourceCategoryDo> selectList(UmsResourceCategoryDo resourceCategoryDo);

    /**
     * 新增资源分类
     * @param umsResourceCategoryDo
     */
    void insert(UmsResourceCategoryDo umsResourceCategoryDo);

    /**
     * 根据资源分类id查找
     * @param id
     * @return
     */
    List<UmsResourceCategoryDo> selectById(Integer id);

    /**
     * 更新资源
     * @param resourceCategoryDo
     */
    void updateById(UmsResourceCategoryDo resourceCategoryDo);

    /**
     * 根据资源分类id删除
     * @param id
     */
    void deleteById(Integer id);
}
