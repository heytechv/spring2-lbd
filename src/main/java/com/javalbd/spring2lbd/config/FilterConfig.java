package com.javalbd.spring2lbd.config;

import com.javalbd.spring2lbd.filter.AuthorizationStudentFilter;
import com.javalbd.spring2lbd.filter.AuthorizationTeacherFilter;
import com.javalbd.spring2lbd.filter.PreAuthorizationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean public FilterRegistrationBean<PreAuthorizationFilter> filterPre() {
        FilterRegistrationBean<PreAuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new PreAuthorizationFilter());
        registrationBean.setOrder(1);

        return registrationBean;
    }

    /** Zad 21 - zakomentowac te i zrobic AuthorizationService + Interceptor */
//    @Bean public FilterRegistrationBean<AuthorizationStudentFilter> filterStudent() {
//        FilterRegistrationBean<AuthorizationStudentFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AuthorizationStudentFilter());
//        registrationBean.setOrder(2);
//        registrationBean.addUrlPatterns("/api/student/*");
//
//        return registrationBean;
//    }
//
//    @Bean public FilterRegistrationBean<AuthorizationTeacherFilter> filterTeacher() {
//        FilterRegistrationBean<AuthorizationTeacherFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AuthorizationTeacherFilter());
//        registrationBean.setOrder(2);
//        registrationBean.addUrlPatterns("/api/teacher/*");
//
//        return registrationBean;
//    }
}
