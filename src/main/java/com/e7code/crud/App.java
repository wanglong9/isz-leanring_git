package com.e7code.crud;

import com.e7code.common.mvc.handler.HttpQueryParamsArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean(name = "queryArgumentResolver")
    public HttpQueryParamsArgumentResolver multipartResolver() {
        HttpQueryParamsArgumentResolver resolver = new HttpQueryParamsArgumentResolver();
        return resolver;
    }
}
