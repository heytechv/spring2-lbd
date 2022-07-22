package com.javalbd.spring2lbd.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component  // automatycznie sie podepnie (bez zbednej konfiguracji)
public class DecimalCheckFilter extends OncePerRequestFilter {

    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().contains("/api/decimal/setdecimal")) {
            if (request.getHeader("decimalPlaces") == null || !Arrays.asList("1", "2", "3", "4").contains(request.getHeader("decimalPlaces"))) {

                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpResponse.setContentType(MediaType.APPLICATION_JSON.toString());
                httpResponse.getWriter().write(HttpStatus.BAD_REQUEST.toString());

                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
