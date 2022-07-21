package com.javalbd.spring2lbd.filter.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Logger log = LoggerFactory.getLogger(AuthenticationSuccessHandler.class);

    // Wstrzykumey z 'application.properties'
    @Value("${jwt.expirationTimeMillis}") private long expirationTimeMillis;
    @Value("${jwt.secret}")               private String secret;


    @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes());

        // Pobieramy nasze UserDetails
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        // Tworzymy token (co ma zawierac)
        String token = Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("authorities", authentication.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMillis))
                .signWith(secretKey)
                .compact();
        // Zwracamy token w headerze
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

}
