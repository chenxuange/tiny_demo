package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.api.ResultCode;
import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.domain.LoginUserDetails;
import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.mapper.*;
import com.example.tiny_demo.modules.ums.model.*;
import com.example.tiny_demo.modules.ums.service.UmsAdminCacheService;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.example.tiny_demo.modules.ums.service.UmsRoleService;
import com.example.tiny_demo.security.utils.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 哪些情况需要加入缓存或删除缓存：
 * 1. 频繁查询的
 * 2. 删除和更新
 *
 *
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminCacheService adminCacheService;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private UmsMenuMapper menuMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UmsAdminRoleRMapper adminRoleRMapper;

    @Autowired
    private UmsAdminLoginLogMapper logMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public UmsAdminDO getAdminByUsername(String username) {
        //  先查用户缓存
        UmsAdminDO admin = adminCacheService.getAdmin(username);
        if (admin != null) {
            return admin;
        }
        UmsAdminDO query = new UmsAdminDO();
        query.setUsername(username);
        List<UmsAdminDO> list = adminMapper.selectList(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        //  添加用户缓存
        admin = list.get(0);
        adminCacheService.setAdmin(admin);
        return admin;
    }

    @Override
    public void register(UmsAdminParam umsAdminParam) {
        UmsAdminDO umsAdminDO = new UmsAdminDO();
        BeanUtils.copyProperties(umsAdminParam, umsAdminDO);
        // 先查询是否有同名用户
        List<UmsAdminDO> adminDOList = adminMapper.selectList(umsAdminDO);
        if (!CollectionUtils.isEmpty(adminDOList)) {
            // 已经存在用户
            Asserts.fail(ResultCode.ADMIN_EXIST_ERROR);
        }
        // 新用户注册时间, 登录时间这时不设置
        umsAdminDO.setCreateTime(new Date());
        // 新增用户默认启用
        umsAdminDO.setStatus(1);
        //  用户密码加密保存
        umsAdminDO.setPassword(passwordEncoder.encode(umsAdminDO.getPassword()));
        adminMapper.insert(umsAdminDO);
    }


    @Override
    public String refreshToken(String oldToken) {
        logger.debug("UmsAdminServiceImpl.refreshToken, oldToken = {}", oldToken);
        String username = jwtTokenUtil.getUserNameFromToken(oldToken);
        // 半小时内，新旧token是相同的
        String newToken = jwtTokenUtil.refreshHeadToken(oldToken);
        if(!oldToken.equals(newToken))
            // 若新token和原token不同，更新操作时间
            updateOperatorTime(null, username, jwtTokenUtil.getCreateTimeFromToken(newToken));
        return newToken;
    }

    @Override
    public PageInfo<UmsAdminDO> list(String keyword, Integer pageNum, Integer pageSize) {
        PageInfo<UmsAdminDO> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> adminMapper.selectByKeyword(keyword));
        return pageInfo;
    }

    @Override
    public void delete(Integer adminId) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(adminId);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        adminMapper.deleteByIdBatch(ids);
        //  移除用户缓存
        adminCacheService.delAdmin(adminId);
        //  移除资源缓存
        adminCacheService.delResourceList(adminId);
    }

    @Override
    public void updateRole(Integer adminId, List<Integer> roleIds) {
        // 更新用户角色，先要删除用户角色关系表中当前用户的所有角色，再添加
        // 删除原用户所有角色
        adminRoleRMapper.delete(adminId);
        List<UmsAdminRoleR> adminRoleRList = roleIds.stream().map(roleId -> {
            UmsAdminRoleR umsAdminRoleR = new UmsAdminRoleR();
            umsAdminRoleR.setAdminId(adminId);
            umsAdminRoleR.setRoleId(roleId);
            return umsAdminRoleR;
        }).collect(Collectors.toList());
        // 插入新的角色列表，实际上角色id应该保证是角色表中已有id，这个前端控制即可
        adminRoleRMapper.insertBatch(adminRoleRList);
        //  移除资源缓存。修改用户角色关系，实际用户信息不变，而用户资源发生变更
        adminCacheService.delResourceList(adminId);
    }

    @Override
    public List<UmsRoleDo> getRoleList(Integer adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResourceDo> getResourceList(Integer adminId) {
        //  先查资源缓存
        List<UmsResourceDo> resourceList = adminCacheService.getResourceList(adminId);
        if (resourceList != null) {
            return resourceList;
        }
        resourceList = resourceMapper.getResourceList(adminId);
        //  添加资源缓存
        adminCacheService.setResourceList(adminId, resourceList);
        return resourceList;
    }

    @Override
    public void updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        // 参数校验，由全局异常处理器统一处理
        // 先查账户
        UmsAdminDO adminDO = new UmsAdminDO();
        adminDO.setUsername(updatePasswordParam.getUsername());
        List<UmsAdminDO> adminDOList = adminMapper.selectList(adminDO);
        if (CollectionUtils.isEmpty(adminDOList)) {
            Asserts.fail("不存在该用户");
        }
        UmsAdminDO umsAdminDO = adminDOList.get(0);
        // 校验密码
        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), umsAdminDO.getPassword())) {
            Asserts.fail("旧密码不正确");
        }
        // 更新密码
        umsAdminDO.setPassword(passwordEncoder.encode(updatePasswordParam.getNewPassword()));
        adminMapper.updateByIdOrUsername(umsAdminDO);
        // 更改密码时：当用户更改密码时，请注意用户数据库中的更改密码时间，因此当更改密码时间大于令牌创建时间时，令牌无效
        updateOperatorTime(null, updatePasswordParam.getUsername(), null);
        //  移除用户缓存
        adminCacheService.delAdmin(umsAdminDO.getId());
    }

    @Override
    public UmsAdminDO get(Integer adminId) {
        // 没有涉及用户jwt验证需要的实时信息变更
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(adminId);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public void updateUser(Integer id, UmsAdminParam adminParam) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        UmsAdminDO adminDO = new UmsAdminDO();
        BeanUtils.copyProperties(adminParam, adminDO);
        adminDO.setId(id);
        adminMapper.updateByIdOrUsername(adminDO);
        //  移除用户缓存
        adminCacheService.delAdmin(id);
    }

    @Override
    public String login(UmsAdminLoginParam adminLoginParam) {
        String token = null;
        try {
            // 核心：通过前端传递的用户名去查找是否存在真实账户,并返回自定义UserDetails
            UserDetails userDetails = loadUserByUsername(adminLoginParam.getUsername());
            if (!passwordEncoder.matches(adminLoginParam.getPassword(), userDetails.getPassword())) {
                Asserts.fail("密码错误");
            }
            if (!userDetails.isEnabled()) {
                Asserts.fail("该账户已被禁用");
            }
            // 登录时设置一个凭证
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //  插入登录日志，修改用户最后一次登录时间
            updateLastLoginTime(userDetails.getUsername());
            insertLoginLog(userDetails.getUsername());
            // 生成全新的token
            token = jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            logger.warn("UmsAdminServiceImpl.login, 登录异常，{}", e.getMessage());
        }
        return token;
    }

    /**
     * 更新最后一次登录时间
     * @param username
     */
    private void updateLastLoginTime(String username) {
        try{
            UmsAdminDO adminDO = new UmsAdminDO();
            adminDO.setUsername(username);
            adminDO.setLoginTime(new Date());
            adminMapper.updateByIdOrUsername(adminDO);
        }catch (Exception e) {
            logger.warn("updateLastLoginTime fail, {}", e.getMessage());
        }
    }

    /**
     * 插入用户 username 的登录日志
     * @param username
     */
    private void insertLoginLog(String username) {
        try {
            UmsAdminDO query = new UmsAdminDO();
            query.setUsername(username);
            List<UmsAdminDO> list = adminMapper.selectList(query);
            // 此时list肯定不为空，无需判断
            UmsAdminLoginLog loginLog = new UmsAdminLoginLog();
            loginLog.setAdminId(list.get(0).getId());
            loginLog.setCreateTime(new Date());
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();
//        String remoteAddr = request.getRemoteAddr();
//        logger.debug("remoteAddr, {}", remoteAddr);
            String addr = httpServletRequest.getRemoteAddr();
            loginLog.setIp(addr);
            logMapper.insert(loginLog);
        } catch (Exception e) {
            logger.warn("insertLoginLog fail, {}", e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UmsAdminDO admin = this.getAdminByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<UmsResourceDo> resourceList = this.getResourceList(admin.getId());
        return new LoginUserDetails(admin, resourceList);
    }

    @Override
    public void updateStatus(Integer adminId, Integer status) {
        List<Integer> ids = new ArrayList<>();
        ids.add(adminId);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        UmsAdminDO adminDO = new UmsAdminDO();
        adminDO.setId(adminId);
        adminDO.setStatus(status);
        adminMapper.updateByIdOrUsername(adminDO);
        //  移除用户缓存
        adminCacheService.delAdmin(adminDO.getId());
    }

    @Override
    public void logout(String name) {
        updateOperatorTime(null, name, null);
    }

    @Override
    public Map<String, Object> info(String username) {
        Map<String, Object> ret = new HashMap<>();
        // 用户已经登录了
        UmsAdminDO umsAdmin = getAdminByUsername(username);
        ret.put("username", umsAdmin.getUsername());
        ret.put("menus", menuMapper.getMenuList(umsAdmin.getId()));
        ret.put("icon", umsAdmin.getIcon());
        List<UmsRoleDo> roleList = getRoleList(umsAdmin.getId());
        if(!CollectionUtils.isEmpty(roleList)){
            List<String> roles = roleList.stream().map(UmsRoleDo::getName).collect(Collectors.toList());
            ret.put("roles",roles);
        }else{
            ret.put("roles",new ArrayList<>());
        }
        return ret;
    }

    /**
     * 根据用户名或用户id更新用户操作时间
     * 更新用户操作时间，jwt验证时需要判断将token失效，对比了 token 中设置的创建时间和用户信息的操作时间
     * 因此，用户信息缓存需要强制删除, 否则对比没意义
     */
    private void updateOperatorTime(Integer id, String username, Date setTime) {
        UmsAdminDO adminDO = new UmsAdminDO();
        // 删除缓存是根据用户id的, 所以先保证找到id
        if(id != null) {
            adminDO.setId(id);
        }else{
            adminDO = adminCacheService.getAdmin(username);
        }
        // 更新用户操作时间前先让缓存失效
        adminCacheService.delAdmin(adminDO.getId());
        if(setTime == null) {
            adminDO.setOperatorTime(new Date());
        }else{
            // 数据库中datetime默认对毫秒数舍并进一位的，所以实际保存时间是当前时间多一秒. 将库中该字段改为datetime(3)保留三位精度解决了
            adminDO.setOperatorTime(setTime);
        }
        logger.info("updateOperatorTime for user, {}", adminDO.getOperatorTime().toLocaleString());
        adminMapper.updateByIdOrUsername(adminDO);
    }
}
