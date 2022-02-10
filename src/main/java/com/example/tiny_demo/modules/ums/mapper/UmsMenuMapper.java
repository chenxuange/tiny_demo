package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsMenuDo;

import java.util.ArrayList;
import java.util.List;

public interface UmsMenuMapper {
    /**
     * 根据菜单id列表批量查询菜单
     * @param ids
     * @return
     */
    List<UmsMenuDo> selectByIdBatch(List<Integer> ids);

    /**
     * 根据菜单条件批量查询菜单
     * @param menuDo
     * @return
     */
    List<UmsMenuDo> selectBatch(UmsMenuDo menuDo);

    /**
     * 添加菜单
     * @param umsMenuDo
     */
    void insert(List<UmsMenuDo> umsMenuDo);

    /**
     * 批量删除菜单
     * @param list
     */
    void deleteByIdBatch(ArrayList<Integer> list);

    /**
     * 更新菜单
     * @param umsMenuDo
     */
    void update(UmsMenuDo umsMenuDo);
}
