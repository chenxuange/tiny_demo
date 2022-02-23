package com.example.tiny_demo.domain;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义安全用户
 */
public class LoginUserDetails implements UserDetails {
    public static final Logger logger = LoggerFactory.getLogger(LoginUserDetails.class);
    // 登录用户
    private UmsAdminDO admin;
    // 登录用户所有资源
    private List<UmsResourceDo> resourceList;

    public LoginUserDetails(UmsAdminDO admin, List<UmsResourceDo> resourceList) {
        this.admin = admin;
        this.resourceList = resourceList;
    }

    // 取出所有权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = resourceList.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getId() + ":" + role.getName())).collect(Collectors.toList());
            // TODO 若控制器方法上添加@PreAuthorize("hasRole('test')")用作权限控制，那么数据库中必须对应ROLE_test，实际控制器@PreAuthorize("hasRole('ROLE_test')")也可以
            /**
             * 1. 根据 SecurityExpressionRoot中 hasRole(role)->hasAnyRole(role)->hasAnyAuthorityName(defaultRolePrefix, roles)->getRoleWithDefaultPrefix(defaultRolePrefix, role)
             * defaultRolePrefix = 'ROLE_'
             * 而 getRoleWithDefaultPrefix 中的逻辑是，若 role有 defaultRolePrefix前缀，那么返回 role；若没有这个前缀，就返回拼接 defaultRolePrefix + role
             * 也就是说，数据库中定义必须是 ROLE_xxx,而方法上 @PreAuthorize("hasRole('xxx')")即可
             * 2. 根据 SecurityExpressionRoot 中 hasAuthority,实际上 defaultRolePrefix 设置为 null ,就不需要考虑前缀了
             */
//            authorities.add(new SimpleGrantedAuthority("ROLE_test"));  // TODO 为了测试
        logger.debug("authorities, {}", authorities);
        return authorities;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    // TODO 为什么这三个都返回true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 账号可用
        return Objects.equals(1, admin.getStatus());
    }

    public UmsAdminDO getAdmin() {
        return admin;
    }

    public List<UmsResourceDo> getResourceList() {
        return resourceList;
    }
}
