package com.example.tiny_demo.config;

import com.example.tiny_demo.domain.LoginUserDetails;
import com.example.tiny_demo.modules.ums.model.UmsAdminDO;
import com.example.tiny_demo.modules.ums.model.UmsResourceDo;
import com.example.tiny_demo.modules.ums.service.UmsAdminService;
import com.example.tiny_demo.modules.ums.service.UmsResourceService;
import com.example.tiny_demo.security.config.SecurityConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * springboot的安全配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MallSecurityConfig extends SecurityConfig {
//    @Autowired
//    private UmsAdminService umsAdminService;
//
//    // UserDetailsService的loadUserByUsername方法实现,根据用户名获取用户信息封装成UserDetails
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return (username -> {
//            UmsAdminDO admin = umsAdminService.getAdminByUsername(username);
//            if(admin == null) {
//                throw  new UsernameNotFoundException("用户名或密码错误");
//            }
//            List<UmsResourceDo> resourceList = umsAdminService.getResourceList(admin.getId());
//            return new LoginUserDetails(admin, resourceList);
//        });
//    }

}
