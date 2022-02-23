package com.example.tiny_demo.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 动态权限决策管理器
 */
public class DynamicAccessDecisionManager implements AccessDecisionManager {
    // 核心,鉴权,根据登录用户和资源列表
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollectionUtils.isEmpty(configAttributes)) {
            // 若资源列表为空，直接放行，不比较
            return;
        }
        // 用户拥有的资源
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> authorizelist = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 访问接口需要的资源
        for(ConfigAttribute configAttribute: configAttributes) {
            String needAuthorize = configAttribute.getAttribute();
            if(authorizelist.contains(needAuthorize)){
                // 用户拥有访问接口需要的资源中任一条，都视为鉴权通过
                return;
            }
        }
        throw new AccessDeniedException("抱歉，您没有相关权限");
    }

    // TODO 下面两个不懂
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
