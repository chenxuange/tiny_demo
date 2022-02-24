package com.example.tiny_demo.security.config;


import com.example.tiny_demo.common.api.CommonResult;
import com.example.tiny_demo.security.component.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import springfox.documentation.spring.web.json.JsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对SpringSecurity的基础配置，后续支持自定义白名单资源路径和动态权限控制
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired(required = false)
    private DynamicSecurityService dynamicSecurityService;

    // 三个有条件的生成bean
    @ConditionalOnBean(DynamicSecurityService.class)
    @Bean
    public DynamicSecurityMetadataSource dynamicSecurityMetadataSource() {
        return new DynamicSecurityMetadataSource();
    }

    @ConditionalOnBean(DynamicSecurityService.class)
    @Bean
    public DynamicAccessDecisionManager dynamicAccessDecisionManager() {
        return new DynamicAccessDecisionManager();
    }


    @ConditionalOnBean(DynamicSecurityService.class)
    @Bean
    public DynamicSecurityFilter dynamicSecurityFilter() {
        return new DynamicSecurityFilter();
    }

    /**
     * 实际上，spring-security中这个配置生效，（不去重写本方法）就会存在一种默认安全实现。
     * 1. 默认开启了防止跨域攻击的功能，任何 POST请求，都需要_csrf参数，可以关闭
     * 2. 默认任何请求都需要认证, 已经登陆就不需要
     * 3. 开启了表单登录，鉴于此，可以内存中配置一位用户，以实现认证通过，但也仅get请求可以通过
     * 小结：无法跨域，存在登陆表单, 无法执行post请求
     *
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);  // 关闭默认安全配置
//        http.csrf().disable(); // security默认拦截器会开启csrf处理，jwt需关闭跨域请求认证
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        // 允许访问不需要保护的资源路径，如swagger或登录相关等，否则swagger接口文档打不开
        for(String url : ignoreUrlsConfig.getUrls()) {
            registry.antMatchers(url).permitAll();
            // 白名单路径访问后，authentication的principal是一个anonymousUser
        }
        // 任何授权请求需要身份认证
        registry.and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 关闭跨站请求防护及不使用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 自定义权限拒绝处理类\认证失败处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器,完成认证
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        if(dynamicSecurityService != null) {
            // 自定义权限过滤拦截器，完成鉴权。若鉴权失败，由 restfulAccessDeniedHandler 处理
            registry.and().addFilterBefore(dynamicSecurityFilter(), FilterSecurityInterceptor.class);
        }
    }


    @Bean
    // 没有权限拒绝访问
    public AccessDeniedHandler restfulAccessDeniedHandler() {
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Cache-Control","no-cache");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().println(objectMapper.writeValueAsString(CommonResult.unauthorized(e.getMessage())));
                response.getWriter().flush();
            }
        };
    }

    /**
     * 若认证失败，不论是没有相关权限、未登录或登录过期，会抛出对应异常；
     * 若设置了全局异常处理器处理任意异常，就会有先处理拦截，此时认证失败处理策略就无法再执行
     */
    @Bean
    // 未登录或登录过期
    public AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Cache-Control","no-cache");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                ObjectMapper objectMapper = new ObjectMapper();
                response.getWriter().println(objectMapper.writeValueAsString(CommonResult.unauthenticated(e.getMessage())));
                response.getWriter().flush();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 内存中设置用户配合默认的安全配置，从而进入登录页面
//        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("admin");
//    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }
}

