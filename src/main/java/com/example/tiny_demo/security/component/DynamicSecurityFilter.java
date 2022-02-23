package com.example.tiny_demo.security.component;

import com.example.tiny_demo.security.config.IgnoreUrlsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    public static final Logger logger = LoggerFactory.getLogger(DynamicSecurityFilter.class);

    @Autowired(required = false)
    private DynamicSecurityMetadataSource securityMetadataSource;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;


    // 放行规则
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // TODO 后续options遇检放行
        // 白名单内的直接放行
        String requestURI = req.getRequestURI();
        logger.debug("ignoreUrls, {}", ignoreUrlsConfig.getUrls());
        logger.debug("requestURI, {}", requestURI);
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String ignoreUrl : ignoreUrlsConfig.getUrls())
            if (pathMatcher.match(ignoreUrl, requestURI)) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                // TODO 上下有区别吗
//            chain.doFilter(request, response);
                return;
            }
        //核心：此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        // 实际里面有 this.accessDecisionManager.decide(authenticated, object, attributes);
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }

    // TODO 不清楚
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return securityMetadataSource;
    }
}
