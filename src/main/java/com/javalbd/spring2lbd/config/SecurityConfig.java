package com.javalbd.spring2lbd.config;

import com.javalbd.spring2lbd.security.PermissionType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


//    @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("")
//    }

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/api/decimal/setdecimal").hasAnyAuthority(PermissionType.DECIMAL_WRITE.name(), PermissionType.ACCESS_ALL.name())
                .antMatchers("/api/multiplier/setmultiplier").hasAnyAuthority(PermissionType.MULTIPLIER_WRITE.name(), PermissionType.ACCESS_ALL.name())

                .anyRequest().permitAll()

                .and()
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }


    @Bean @Override public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User
                .withUsername("admin")
                .password(encoder().encode("admin"))
                .authorities(PermissionType.ACCESS_ALL.name())
                .build();

        UserDetails decimal = User
                .withUsername("decimal")
                .password(encoder().encode("decimal"))
                .authorities(PermissionType.DECIMAL_READ.name(), PermissionType.DECIMAL_WRITE.name())
                .build();

        UserDetails multi = User
                .withUsername("multi")
                .password(encoder().encode("multi"))
                .authorities(PermissionType.MULTIPLIER_READ.name(), PermissionType.MULTIPLIER_WRITE.name())
                .build();

        return new InMemoryUserDetailsManager(
                admin,
                decimal,
                multi
        );
    }

    @Bean PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

}
