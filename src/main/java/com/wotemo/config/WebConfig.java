package com.wotemo.config;

import com.wotemo.interceptor.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginCheckInterceptor loginCheckInterceptor;
    @Autowired
    public WebConfig(LoginCheckInterceptor loginCheckInterceptor){
        this.loginCheckInterceptor = loginCheckInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/register/**")
                .excludePathPatterns("/upload")
                .excludePathPatterns("/logout/**")
                .excludePathPatterns("/verify-code")
                .excludePathPatterns("/remove/verify-code")
                .excludePathPatterns("/get/**");
    }
}
