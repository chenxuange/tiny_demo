package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsMenuNode;
import com.example.tiny_demo.modules.ums.model.UmsMenuDo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMenuService {
    /**
     * 根据菜单id查询菜单
     * @param id
     * @return
     */
    UmsMenuDo selectById(Integer id);

    /**
     * 根据父类id搜索所有菜单
     * @param parentId
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<UmsMenuDo> selectPage(Integer parentId, Integer pageNum, Integer pageSize);

    /**
     * 树级菜单
     * @return
     */
    List<UmsMenuNode> treeList();

    /**
     * 新增菜单,添加事务处理
     * @param umsMenuDo
     */
    @Transactional
    void create(UmsMenuDo umsMenuDo);

    /**
     * 根据菜单id删除菜单
     * @param id
     */
    @Transactional
    void delete(Integer id);

    /**
     * 更新菜单
     * @param id
     * @param umsMenuDo
     */
    @Transactional
    void update(Integer id, UmsMenuDo umsMenuDo);

    /**
     * 更新菜单的状态
     * @param id
     * @param hidden
     */
    @Transactional
    void updateHidden(Integer id, Integer hidden);
}
