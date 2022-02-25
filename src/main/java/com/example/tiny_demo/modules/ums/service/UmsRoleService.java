package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsRoleParam;
import com.example.tiny_demo.modules.ums.model.*;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsRoleService {
    /**
     * 获取所有角色
     * @return
     */
    List<UmsRoleDo> listAll();

    /**
     * 根据角色名称模糊查找，包含分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<Object> list(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据角色id获取关联菜单
     * @param roleId
     * @return
     */
    List<UmsMenuDo> listMenu(Integer roleId);

    /**
     * 根据角色id获取关联资源
     * @param roleId
     * @return
     */
    List<UmsResourceDo> listResource(Integer roleId);

    /**
     * 新增角色
     * @param roleParam
     */
    @Transactional
    void create(UmsRoleParam roleParam);

    /**
     * 更新角色一般信息，不影响其他
     * @param id
     * @param roleParam
     */
    @Transactional
    void update(Integer id, UmsRoleParam roleParam);

    /**
     * 更新角色状态
     * @param id
     * @param status
     */
    @Transactional
    void updateStatus(Integer id, Integer status);

    /**
     * 批量删除角色
     * @param ids
     */
    @Transactional
    void deleteBatch(List<Integer> ids);

    /**
     * 给角色分配菜单
     * @param roleId
     * @param menuIds
     */
    void allocMenu(Integer roleId, List<Integer> menuIds);

    /**
     * 给角色分配资源
     * @param roleId
     * @param resourceIds
     */
    void allocResource(Integer roleId, List<Integer> resourceIds);


}
