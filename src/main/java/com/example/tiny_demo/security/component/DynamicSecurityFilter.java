package com.example.tiny_demo.security.component;


import com.example.tiny_demo.security.config.IgnoreUrlsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 安全拦截器，鉴权
 */
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    public static final Logger logger = LoggerFactory.getLogger(DynamicSecurityFilter.class);

    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    // 放行规则
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        // TODO 后续options遇检放行
        // 白名单内的直接放行, 不需要鉴权
        String requestURI = req.getRequestURI();
        logger.info("request url: {}", requestURI);
        // TODO 为什么这个地方会执行两次, 一次是来的时候，一次是返回的时候
//        logger.debug("request, {}", request);
//        logger.debug("response, {}", response);
        AntPathMatcher pathMatcher = new AntPathMatcher();
        for (String ignoreUrl : ignoreUrlsConfig.getUrls())
            if (pathMatcher.match(ignoreUrl, requestURI)) {
                logger.debug("ignoreUrl, {}", ignoreUrl);
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                //  上下没区别
//                chain.doFilter(request, response);
                return;
            }
        //核心：此处会调用AccessDecisionManager中的decide方法进行鉴权操作
        // 实际里面有 this.accessDecisionManager.decide(authenticated, object, attributes);
        /**
         * 核心就是下面三条，
         * 1.先去拿到安全元数据来源并调用getAttributes获取安全配置
         * 2.再去获取认证
         * 3. 再去鉴权
         * 		Collection<ConfigAttribute> attributes = this.obtainSecurityMetadataSource()
         * 				.getAttributes(object);
         * 		Authentication authenticated = authenticateIfRequired();
         * 		this.accessDecisionManager.decide(authenticated, object, attributes);
         */
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }

    // 必须返回安全对象，即自定义安全拦截器，否则 Error creating bean with name 'dynamicSecurityFilter'
    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
