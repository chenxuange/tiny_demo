package com.example.tiny_demo.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
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
    public static final Logger logger = LoggerFactory.getLogger(DynamicAccessDecisionManager.class);
    // 核心,鉴权,根据登录用户和资源列表
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollectionUtils.isEmpty(configAttributes)) {
            // 若访问当前接口所需的安全配置列表为空，直接放行，即允许访问
            return;
        }
        // 用户拥有的资源授权
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> allAuthorize = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 访问接口需要的资源
        for(ConfigAttribute configAttribute: configAttributes) {
            String needAuthorize = configAttribute.getAttribute();
            if(allAuthorize.contains(needAuthorize)){
                // 用户拥有访问接口需要的资源中任一条，都视为鉴权通过
                logger.debug("haveAuthorized, {}", allAuthorize);
                logger.debug("needAuthorize, {}", needAuthorize);
                logger.debug("decide pass, {}", true);
                return;
            }
        }
        throw new AccessDeniedException("请确认您的操作权限");
    }

    // 若DynamicSecurityMetadataSource中 getAllConfigAttributes 返回null,这个方法不会执行;
    // 若 getAllConfigAttributes 返回安全配置集合，supports 可针对对待某些特定的不安全配置，返回false,则无法成功创建决策管理器
    // 推荐无条件返回true,则不处理所有
    // 指示此 AccessDecisionManager 是否能够处理由传递的ConfigAttribute表示的授权请求
    @Override
    public boolean supports(ConfigAttribute attribute) {
        // 测试 29:后台资源管理
//        String filter = "29:后台资源管理";
//        logger.info("attribute, {}", attribute);
//        if(filter.equals(attribute.getAttribute())) {
//            return false;
//        }
        return true;
    }

    // 必须true，否则自定义这个实现类没意义。指示AccessDecisionManager实现是否能够为指定的受保护对象类型提供访问控制决策
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
