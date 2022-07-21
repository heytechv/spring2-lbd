package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.security.LoginCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    /** Za uwierzytelnianie bedzie odpowiadal filtr, ta metoda jest zeby w swaggerze byla widoczna
     *      UWAGA! Domyslnie UsernamePasswordAuthenticationFilter dziala tylko dla GET "/login"
     *      jak mamy inne to zmieniamy dodajac w SecurityConfig.java w filtrze authenticationJsonFilter()
     *          jsonObjectAuthenticationFilter.setFilterProcessesUrl("/api/login"); */
    @GetMapping("/login")
    public String login(@RequestBody LoginCredentials credentials) {
        return "Hello!";
    }

}
