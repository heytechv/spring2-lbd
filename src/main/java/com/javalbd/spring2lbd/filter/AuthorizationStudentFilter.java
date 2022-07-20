package com.javalbd.spring2lbd.filter;

import com.javalbd.spring2lbd.component.AuthRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

//@Component @Order(2) - wylaczone bo uzywam konfiguracji

// TODO moze lepiej https://www.baeldung.com/spring-onceperrequestfilter ?
public class AuthorizationStudentFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationStudentFilter.class);

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.info("Student");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String headerRole = httpRequest.getHeader("role");

        if (headerRole == null || !Arrays.asList(AuthRole.TEACHER_ROLE.toString(), AuthRole.STUDENT_ROLE.toString()).contains(headerRole)) {
            // TODO tutaj chyba lepiej throw i obsluzyc
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpResponse.getWriter().write("{\"errorMessage\": \"User unauthorized!\"}");

            return;
        }

        // Continue chains of filter
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
