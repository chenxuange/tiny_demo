package com.example.tiny_demo.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 对SpringSecurity的配置的扩展，后续支持自定义白名单资源路径和动态权限控制
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("123")).roles("admin");
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
        http.csrf().disable();
    }
}

