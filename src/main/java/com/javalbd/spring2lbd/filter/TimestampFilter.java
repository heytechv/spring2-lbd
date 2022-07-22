package com.javalbd.spring2lbd.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

@Component  // automatycznie sie podepnie (bez zbednej konfiguracji)
public class TimestampFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(TimestampFilter.class);


    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("/api/number/getcalculatedvalues")) {
            filterChain.doFilter(request, response);
            return;
        }

        Instant startTime = Instant.now();
        // Continue operations
        filterChain.doFilter(request, response);
        // Log duration
        logger.info("{}: {} ms", request.getRequestURI(), Duration.between(startTime, Instant.now()).toMillis());
    }

}
