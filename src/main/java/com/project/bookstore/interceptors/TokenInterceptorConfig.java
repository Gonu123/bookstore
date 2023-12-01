package com.project.bookstore.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebMvc
public class TokenInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Map<String, String> protectedUrls = new HashMap<>();
        protectedUrls.put("/idp/user", "GET");
        protectedUrls.put("/order", "POST");
        protectedUrls.put("/orders", "GET");
        registry.addInterceptor(new TokenInterceptor(protectedUrls));
    }
}
