package com.example.tiny_demo.config;

import com.example.tiny_demo.common.config.BaseSwagger2Config;
import com.example.tiny_demo.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * springBoot中Swagger开发配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config extends BaseSwagger2Config {
    @Override
    protected SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.example.tiny_demo.modules.ums")
                .title("最小权限项目骨架")
                .description("相关接口文档")
                .contactName("valerius")
                .version("1.0")
                .enableSecurity(true)  //  登录认证
                .build();
    }
}
