package com.wj.jwt.config;

import com.wj.jwt.handler.AuthorisationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthorisationInterceptor authorazationIntercepter(){
        return new AuthorisationInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(authorazationIntercepter())
        //指定需要拦截的路径
        .addPathPatterns("/user/**");

    }

}
