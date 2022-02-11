package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.query.UmsAdminQuery;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UmsAdminMapper {

    /**
     * 根据查询条件查询用户
     * @param umsAdmin
     * @return
     */
    List<UmsAdminDO> selectList(@Param("umsAdmin") UmsAdminDO umsAdmin);

    /**
     * 根据用户id批量查询
     * @param ids
     * @return
     */
    List<UmsAdminDO> selectByIdBatch(List<Integer> ids);

    /**
     * 新增用户
     * @param umsAdminDO
     */
    void insert(UmsAdminDO umsAdminDO);

    /**
     * 批量删除用户
     * @param ids
     */
    int deleteByIdBatch(ArrayList<Integer> ids);
}
