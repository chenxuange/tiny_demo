package com.example.tiny_demo;

import com.example.tiny_demo.config.MallSecurityConfig;
import com.example.tiny_demo.modules.ums.service.UmsResourceService;
import com.example.tiny_demo.security.component.DynamicSecurityFilter;
import com.example.tiny_demo.security.component.DynamicSecurityMetadataSource;
import com.example.tiny_demo.security.component.DynamicSecurityService;
import com.example.tiny_demo.security.component.JwtAuthenticationTokenFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class TinyDemoApplicationTests {

    @Autowired
    private ApplicationContext context;

    /**
     * 自动创建bean的注入属性肯定有先后顺序,暂时还不清楚
     */
    @Test
    void contextLoads() {
        JwtAuthenticationTokenFilter bean = context.getBean(JwtAuthenticationTokenFilter.class);
        System.out.println(bean);
        UmsResourceService resourceService = context.getBean(UmsResourceService.class);
        System.out.println(resourceService);
        MallSecurityConfig bean1 = context.getBean(MallSecurityConfig.class);
        System.out.println(bean1);
        DynamicSecurityService bean2 = context.getBean(DynamicSecurityService.class);
        System.out.println(bean2);
        DynamicSecurityMetadataSource bean3 = context.getBean(DynamicSecurityMetadataSource.class);
        System.out.println(bean3);
        DynamicSecurityFilter bean4 = context.getBean(DynamicSecurityFilter.class);
        System.out.println(bean4);
    }

}
