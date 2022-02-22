package com.example.tiny_demo.modules.ums.service.impl;

import com.example.tiny_demo.common.api.ResultCode;
import com.example.tiny_demo.common.exception.Asserts;
import com.example.tiny_demo.domain.LoginUserDetails;
import com.example.tiny_demo.modules.ums.dto.UmsAdminLoginParam;
import com.example.tiny_demo.modules.ums.dto.UmsAdminParam;
import com.example.tiny_demo.modules.ums.dto.UpdateAdminPasswordParam;
import com.example.tiny_demo.modules.ums.mapper.*;
import com.example.tiny_demo.modules.ums.model.*;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    private static final Logger logger = LoggerFactory.getLogger(UmsAdminServiceImpl.class);

    @Autowired
    private UmsAdminMapper adminMapper;
    @Autowired
    private UmsRoleMapper roleMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

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
        UmsAdminDO query = new UmsAdminDO();
        query.setUsername(username);
        List<UmsAdminDO> list = adminMapper.selectList(query);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public UmsAdminDO register(UmsAdminParam umsAdminParam) {
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
        return umsAdminDO;
    }


    @Override
    public String refreshToken(String oldToken) {
        logger.debug("UmsAdminServiceImpl.refreshToken, oldToken = {}", oldToken);
        return jwtTokenUtil.refreshHeadToken(oldToken);
    }

    @Override
    public PageInfo<UmsAdminDO> list(String keyword, Integer pageNum, Integer pageSize) {
        PageInfo<UmsAdminDO> pageInfo = PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> adminMapper.selectByKeyword(keyword));
        return pageInfo;
    }

    @Override
    public void delete(Integer id) {
        // TODO 用户缓存以后再说
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            Asserts.fail("该用户不存在");
        }
        // 不要一开始就把所有状况想清楚
        adminMapper.deleteByIdBatch(ids);
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
    }

    @Override
    public List<UmsRoleDo> getRoleList(Integer adminId) {
        return roleMapper.getRoleList(adminId);
    }

    @Override
    public List<UmsResourceDo> getResourceList(Integer adminId) {
        List<UmsResourceDo> list = resourceMapper.getResourceList(adminId);
        return list;
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
        // TODO 1. 更改密码时：当用户更改密码时，请注意用户数据库中的更改密码时间，因此当更改密码时间大于令牌创建时间时，令牌无效
    }

    @Override
    public UmsAdminDO get(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        List<UmsAdminDO> list = adminMapper.selectByIdBatch(ids);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public UmsAdminDO updateUser(Integer id, UmsAdminParam adminParam) {
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
        return adminDO;
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
            // 生成token
            token = jwtTokenUtil.generateToken(userDetails);
            // TODO 插入登录日志，修改用户最后一次登录时间
            updateLastLoginTime(userDetails.getUsername());
            insertLoginLog(userDetails.getUsername());
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
}
