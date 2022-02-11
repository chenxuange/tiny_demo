package com.example.tiny_demo.modules.ums.service;

import com.example.tiny_demo.modules.ums.UmsAdminParam;
import com.example.tiny_demo.modules.ums.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResource;
import com.example.tiny_demo.modules.ums.model.UmsRoleDo;
import com.github.pagehelper.Page;
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
     * 登录功能
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username,String password);

    /**
     * 刷新token的功能
     * @param oldToken 旧的token
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     */
    Page<UmsAdminDO> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     */
    boolean update(Long id, UmsAdminDO admin);

    /**
     * 删除指定用户
     * @param id
     */
    @Transactional
    boolean delete(Integer id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

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
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 根基用户id获取用户
     * @param id
     * @return
     */
    UmsAdminDO get(Integer id);

    /**
     * 获取用户信息
     */
//    UserDetails loadUserByUsername(String username);
}
