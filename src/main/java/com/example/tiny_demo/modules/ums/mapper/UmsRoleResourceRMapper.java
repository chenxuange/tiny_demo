package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsRoleResourceR;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleResourceRMapper {
    /**
     * 分配角色下资源
     * @param list
     */
    @Transactional
    void insertBatch(List<UmsRoleResourceR> list);

    /**
     * 删除指定角色id下的资源
     * @param roleId
     */
    @Transactional
    void deleteByRoleId(Integer roleId);
}
