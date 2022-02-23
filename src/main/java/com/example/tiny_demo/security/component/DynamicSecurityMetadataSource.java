package com.example.tiny_demo.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *动态权限数据源
 */
public class DynamicSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    public static final Logger logger = LoggerFactory.getLogger(DynamicSecurityMetadataSource.class);

    // 后台资源映射
    private static Map<String, ConfigAttribute> configAttributeMap = null;

    @Autowired
    private DynamicSecurityService dynamicSecurityService;

    // 初始化资源
    @PostConstruct
    public void init() {
        dynamicSecurityService.loadDataSource();
    }

    // 释放资源，当资源有了更新后释放
    public void clear() {
        configAttributeMap.clear();
        configAttributeMap = null;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            // 若资源映射为空，重新加载
            configAttributeMap = dynamicSecurityService.loadDataSource();
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        String path = requestUrl;
        logger.debug("requestUrl, {}", requestUrl);
        logger.debug("path, {}", path);
        // 获取访问该路径所匹配到的资源列表
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(Map.Entry<String, ConfigAttribute> entry : configAttributeMap.entrySet()){
            String pattern = entry.getKey();
            if(antPathMatcher.match(pattern, path)) {
                // 遇到匹配就加入
                configAttributes.add(entry.getValue());
            }
        }
        //
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
