package com.example.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

//Swagger 配置类
@Slf4j
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket permissionAdmin(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台系统功能接口")
                .apiInfo(apiInfo())
                .enable(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.system"))
                .build();
    }

    @Bean
    public Docket permissionHtmlAdmin(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("前台系统功能接口")
                .apiInfo(apiInfo())
                .enable(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.admin"))
                .build();
    }

    //配置文档信息
    private ApiInfo apiInfo() {
        Contact contact = new Contact("YY", "localhost:8080", "12adf@adf.com");
        return new ApiInfo(
                "各个功能模块的接口", // 标题
                "可以测试各个接口", // 描述
                "v1.0", // 版本
                "localhost:8080", // 组织链接
                contact, // 联系人信息
                "Apach 2.0 许可", // 许可
                "localhost:8080", // 许可连接
                new ArrayList<>()// 扩展
        );
    }

}
