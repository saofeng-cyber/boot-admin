package com.saofeng.admin.config;

import com.saofeng.admin.utils.BootInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BootInterceptor()).addPathPatterns("/admin/*").excludePathPatterns("/admin/login", "/admin/register");
    }
}
