package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsResourceParam;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsResourceService {
    /**
     * 根据条件查询资源
     * @param resourceDo
     * @return
     */
    List<UmsResourceDo> list(UmsResourceDo resourceDo);

    /**
     * 根据资源id查询
     * @param id
     * @return
     */
    UmsResourceDo getById(Integer id);

    /**
     * 分页模糊查询后台资源
     * @param categoryId
     * @param nameKeyword
     * @param urlKeyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<UmsResourceDo> list(Integer categoryId, String nameKeyword, String urlKeyword, Integer pageNum, Integer pageSize);

    /**
     * 新增资源
     * @param resourceParam
     */
    @Transactional
    void create(UmsResourceParam resourceParam);

    /**
     * 删除资源
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 更新资源
     * @param id
     * @param resourceParam
     */
    @Transactional
    void update(Integer id, UmsResourceParam resourceParam);

}
