package com.example.tiny_demo.modules.ums.mapper;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface UmsAdminMapper {

    /**
     * 根据用户名或昵称关键字模糊查找用户
     * @param keyword
     * @return
     */
    List<UmsAdminDO> selectByKeyword(String keyword);

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

    /**
     * 根据用户id或用户名来更新用户
     * @param adminDO
     * @return
     */
    int updateByIdOrUsername(UmsAdminDO adminDO);

    /**
     * 根据资源id找到拥有本资源的用户id列表
     * @param resourceId
     * @return
     */
    List<Integer> getAdminIdList(Integer resourceId);
}
