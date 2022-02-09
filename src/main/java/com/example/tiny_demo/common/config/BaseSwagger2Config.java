package com.example.tiny_demo.common.config;

import com.example.tiny_demo.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * swagger2 基础抽象配置类
 */
public abstract class BaseSwagger2Config {
    // 自定义swagger配置properties
    protected abstract SwaggerProperties swaggerProperties();

    // Swagger实例Bean是Docket，所以必须通过配置Docket实例来配置Swaggger
    @Bean
    public Docket createRestApi() {
        // 实例化swagger的properties
        SwaggerProperties swaggerProperties = swaggerProperties();
        // 构造函数传入初始化规范，这是swagger2规范
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                // apiInfo： 添加api详情信息，参数为ApiInfo类型的参数，这个参数包含了比如标题、描述、版本之类的，开发中一般都会自定义这些信息
                .apiInfo(apiInfo(swaggerProperties))
                // API分组：如果没有配置分组默认是default
                .groupName("v1")
                // 配置是否启用Swagger，如果是false，在浏览器将无法访问，默认是true
                .enable(true)
                .select()
                // apis： 添加过滤条件，扫描生成api的包
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getApiBasePackage()))
                // paths： 这里是控制哪些路径的api会被显示出来
                .paths(PathSelectors.any())
                .build();
        // TODO 待定
//        if (swaggerProperties.isEnableSecurity()) {
//            docket.securitySchemes(securitySchemes()).securityContexts(securityContexts());
//        }
        return docket;
    }


    // 开发基本描述
    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        // 设置联系人，比如名字，个人主页，联系邮箱
        Contact contact = new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail());
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .contact(contact)
                .version(swaggerProperties.getVersion())
                .build();
    }
}
