package com.example.tiny_demo.security.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
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
        configAttributeMap = dynamicSecurityService.loadDataSource();
        logger.debug("init configAttributeMap, {}", configAttributeMap);
    }

    // 释放资源，当资源有了更新后释放
    public void clear() {
        configAttributeMap.clear();
        configAttributeMap = null;
        logger.debug("clear configAttributeMap...");
    }

    /**
     * 获取访问当前路径需要的安全配置
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            // 若资源映射为空，重新加载
            configAttributeMap = dynamicSecurityService.loadDataSource();
            logger.debug("reload configAttributeMap, {}", configAttributeMap);
        }
        List<ConfigAttribute> configAttributes = new ArrayList<>();
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String requestUrl = filterInvocation.getRequestUrl();
        // TODO 若路径中不存在中文，requestUrl就是正常，否则会将中文转化为%xx等形式, 这时就需要转换
        String path = requestUrl;
        logger.debug("path, {}", path);
        // 获取访问该路径所需要的资源列表
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(Map.Entry<String, ConfigAttribute> entry : configAttributeMap.entrySet()){
            String pattern = entry.getKey();
            if(antPathMatcher.match(pattern, path)) {
                logger.debug("pattern, {}", pattern);
                logger.debug("matched configAttribute, {}",entry.getValue());
                // 若资源映射列表中的某个地址能够匹配当前路径，则把该地址对应的安全配置（资源id+资源名字）存入ConfigAttributes
                configAttributes.add(entry.getValue());
            }
        }
        //  返回当前请求允许的安全配置属性集合
        logger.info("need configAttributes, {}", configAttributes);
        return configAttributes;
    }

    // 未使用，因为不是纯粹的一个安全集合，而是根据url的安全映射。额外的，可返回一个所有的资源接口（不考虑允否）交给决策管理器的supports去判断是否合法
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        if (configAttributeMap != null) {
//            return configAttributeMap.values();
//        }
        return null;
    }

    // 表明安全数据源的实现类能够为指定安全对象类型提供ConfigAttributes
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
