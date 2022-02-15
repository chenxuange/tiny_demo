package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResource;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    UmsAdminDO register(UmsAdminParam umsAdminParam);



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
     * 修改指定用户信息
     */
    @Transactional
    boolean update(Long id, UmsAdminDO admin);

    /**
     * 删除指定用户
     * @param id
     * @return
     */
    @Transactional
    void delete(Integer id);

    /**
     * 修改用户角色关系
     * @param adminId
     * @param roleIds
     */
    @Transactional
    void updateRole(Integer adminId, List<Integer> roleIds);

    /**
     * 获取用户对于角色
     * @param adminId
     */
    List<UmsRoleDo> getRoleList(Integer adminId);

    /**
     * 获取指定用户的可访问资源
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改密码
     */
    @Transactional
    void updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 根基用户id获取用户
     * @param id
     * @return
     */
    UmsAdminDO get(Integer id);

    /**
     * 更新用户信息
     * @param id
     * @param adminParam
     * @return
     */
    @Transactional
    UmsAdminDO updateUser(Integer id, UmsAdminParam adminParam);

    /**
     * 登录用户，jwt返回token
     * @param adminLoginParam
     */
    String login(UmsAdminLoginParam adminLoginParam);

    /**
     * 获取用户信息
     */
//    UserDetails loadUserByUsername(String username);
}
