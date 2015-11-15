package com.qianmi.config;

import com.qianmi.filter.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes = new String[] {
                "/",
                "/login",
                "/logout",
                "/weixin",
                "/event/**",
                "/user/bind", // 排除循环判断
                "/static/**",
                "/favicon**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/error"
        };
        securityInterceptor.setExcludes(Arrays.asList(excludes));
        securityInterceptor.setRedirectUrl("/login");
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
