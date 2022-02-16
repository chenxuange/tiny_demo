package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsResourceCategoryParam;
import com.example.tiny_demo.modules.ums.model.UmsResourceCategoryDo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsResourceCategoryService {

    /**
     * 获取所有资源分类列表
     */
    List<UmsResourceCategoryDo> listAll();

    /**
     * 新增资源分类
     * @param resourceCategoryParam
     */
    @Transactional
    UmsResourceCategoryDo create(UmsResourceCategoryParam resourceCategoryParam);

    /**
     * 更新资源分类
     * @param id
     * @param resourceCategoryParam
     * @return
     */
    @Transactional
    UmsResourceCategoryDo update(Integer id, UmsResourceCategoryParam resourceCategoryParam);

    /**
     * 删除资源分类
     * @param id
     */
    @Transactional
    void delete(Integer id);
}
