package com.example.tiny_demo.domain;

import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义安全用户
 */
public class LoginUserDetails implements UserDetails {
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
        System.out.println("LoginUserDetails.getAuthorities : "  + authorities.toString());
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
}
