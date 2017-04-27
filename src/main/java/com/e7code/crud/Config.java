package com.e7code.crud;

import com.e7code.common.mvc.handler.HttpQueryParamsArgumentResolver;
import com.e7code.common.mvc.handler.SecurityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by ssr on 2017/3/21.
 */
@Configuration
public class Config extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/**"）.excludePathPatterns("/admin/**");
        registry.addInterceptor(new  SecurityInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new HttpQueryParamsArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

}
