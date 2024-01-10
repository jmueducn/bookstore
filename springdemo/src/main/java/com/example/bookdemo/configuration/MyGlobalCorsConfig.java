package com.example.bookdemo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyGlobalCorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/graphql") // GraphQL端点
                .allowedOrigins("http://localhost:3000") // 允许的源
                .allowedMethods("GET", "POST") // 允许的方法
                .allowedHeaders("*") // 允许的头部
                .allowCredentials(true); // 是否允许凭证
    }
}
