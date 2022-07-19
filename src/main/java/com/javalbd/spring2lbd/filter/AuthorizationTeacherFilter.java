package com.javalbd.spring2lbd.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//@Component
@Order(2)
@WebFilter("/api/teacher/*")
public class AuthorizationTeacherFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationTeacherFilter.class);

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("Teacher");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
