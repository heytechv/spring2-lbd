package com.javalbd.spring2lbd.config;


import com.javalbd.spring2lbd.interceptor.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    // Autowired bo w przeciwienstwie do Filter uzylem @Component :/ tu tak, tam inaczej wiec TODO ujdednolicic
    @Autowired AuthorizeInterceptor authorizeInterceptor;

    @Override public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizeInterceptor);
    }

}
