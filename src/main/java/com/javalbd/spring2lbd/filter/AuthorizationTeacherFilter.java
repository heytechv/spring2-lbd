package com.javalbd.spring2lbd.filter;

import com.javalbd.spring2lbd.component.AuthRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@Component @Order(2) - wylaczone bo uzywam konfiguracji

// TODO moze lepiej https://www.baeldung.com/spring-onceperrequestfilter ?
public class AuthorizationTeacherFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationTeacherFilter.class);

    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.info("Teacher");

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String roleHeader = httpRequest.getHeader("role");

        if (roleHeader == null || !roleHeader.equals(AuthRole.TEACHER_ROLE.toString())) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setContentType(MediaType.APPLICATION_JSON.toString());
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("{\"errorMessage\": \"User unauthorized!\"}");

            return;
        }

        // Continue chains of filter
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
