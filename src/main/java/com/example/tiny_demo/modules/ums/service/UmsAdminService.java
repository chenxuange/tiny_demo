package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.github.pagehelper.PageInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 用户操作service
 */
public interface UmsAdminService {
    /**
     * 根据用户名获取后台管理员
     */
    UmsAdminDO getAdminByUsername(String username);

    /**
     * 注册功能
     */
    @Transactional
    void register(UmsAdminParam umsAdminParam);



    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    PageInfo<UmsAdminDO> list(String keyword, Integer pageSize, Integer pageNum);


    /**
     * 删除指定用户
     * @param adminId
     * @return
     */
    @Transactional
    void delete(Integer adminId);

    /**
     * 修改用户角色关系
     * @param adminId
     * @param roleIds
     */
    @Transactional
    void updateRole(Integer adminId, List<Integer> roleIds);

    /**
     * 获取用户的所有角色
     * @param adminId
     */
    List<UmsRoleDo> getRoleList(Integer adminId);

    /**
     * 获取指定用户id的可访问资源
     * @param adminId
     */
    List<UmsResourceDo> getResourceList(Integer adminId);

    /**
     * 修改密码
     */
    @Transactional
    void updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 根据用户id获取用户
     * @param adminId
     * @return
     */
    UmsAdminDO get(Integer adminId);

    /**
     * 更新用户信息
     * @param id
     * @param adminParam
     * @return
     */
    @Transactional
    void updateUser(Integer id, UmsAdminParam adminParam);

    /**
     * 登录用户，jwt返回token
     * @param adminLoginParam
     */
    String login(UmsAdminLoginParam adminLoginParam);

    /**
     * 根据用户名查询用户基础信息与拥有权限并封装为UserDetails
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 修改指定用户状态
     * @param adminId
     * @param status
     */
    @Transactional
    void updateStatus(Integer adminId, Integer status);

    /**
     * 注销用户
     * @param name
     */
    @Transactional
    void logout(String name);

    /**
     * 获取当前登录用户信息
     * @param username
     * @return
     */
    Map<String, Object> info(String username);
}
