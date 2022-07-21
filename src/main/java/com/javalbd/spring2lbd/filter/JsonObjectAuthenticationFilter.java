package com.javalbd.spring2lbd.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javalbd.spring2lbd.security.LoginCredentials;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonObjectAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            // Mapujemy json JWT na nasza klase LoginCredentials
            LoginCredentials authRequest = new ObjectMapper().readValue(request.getInputStream(), LoginCredentials.class);
            // Tworzymy token
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
            );
            // Przekazujemy token dalej do Details oraz AuthenticationManager
            setDetails(request, token);
            return this.getAuthenticationManager().authenticate(token);

        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
