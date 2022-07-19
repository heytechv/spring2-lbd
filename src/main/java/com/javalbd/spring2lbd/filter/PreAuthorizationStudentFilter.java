package com.javalbd.spring2lbd.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component
@Order(1)
public class PreAuthorizationStudentFilter implements Filter {

    private final Logger LOGGER = LoggerFactory.getLogger(PreAuthorizationStudentFilter.class);

    /** Zad 18 - roznica czasu
     * <a href="https://stackoverflow.com/questions/42857658/how-to-log-time-taken-by-rest-web-service-in-spring-boot">...</a> */
    @Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Instant instantStart = Instant.now();

        // Continue chains of filter
        filterChain.doFilter(servletRequest, servletResponse);

        LOGGER.info("{}: {} ms", ((HttpServletRequest) servletRequest).getRequestURI(), Duration.between(instantStart, Instant.now()).toMillis());
    }

}
