package com.javalbd.spring2lbd.config;

import com.javalbd.spring2lbd.filter.JsonObjectAuthenticationFilter;
import com.javalbd.spring2lbd.jwt.AuthenticationFailureHandler;
import com.javalbd.spring2lbd.jwt.AuthenticationSuccessHandler;
import com.javalbd.spring2lbd.security.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity(debug = false)                                      // enable WebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      // enable @PreAuthorize("hasAnyRole(...)")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /** Wstrzykujemy handlery */
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;


    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(encoder().encode("user"))
                .authorities(UserPermission.USER_READ.name());
    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(authenticationJsonFilter())  // dodajemy nasz filtr
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }


    @Bean public JsonObjectAuthenticationFilter authenticationJsonFilter() throws Exception {
        JsonObjectAuthenticationFilter jsonObjectAuthenticationFilter = new JsonObjectAuthenticationFilter();
        // ustawiamy successHandler oraz failureHandler
        jsonObjectAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        jsonObjectAuthenticationFilter.setAuthenticationFailureHandler(failureHandler);
        // domyslny authenticationManager
        jsonObjectAuthenticationFilter.setAuthenticationManager(super.authenticationManager());

        /** UWAGA! Domyslnie UsernamePasswordAuthenticationFilter dziala tylko dla GET "/login"
            jak mamy inne to zmieniamy tutaj */
//        jsonObjectAuthenticationFilter.setFilterProcessesUrl("/api/login");

        return jsonObjectAuthenticationFilter;
    }

    @Bean public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

}
