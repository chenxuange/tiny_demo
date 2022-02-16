package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsRoleMenuR;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleMenuRMapper {
    /**
     * 新增角色管理的菜单
     * @param list
     */
    @Transactional
    void insertBatch(List<UmsRoleMenuR> list);

    /**
     * 删除指定角色id下的菜单
     * @param roleId
     */
    void deleteByRoleId(Integer roleId);
}
