package com.javalbd.spring2lbd.config;

import com.javalbd.spring2lbd.security.UserPermission;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity                                      // enable WebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)      // enable @PreAuthorize("hasAnyRole(...)")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic();
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/whoami").hasAnyRole("USER", "ADMIN")
//                .anyRequest().permitAll()
//                .and()
//                .formLogin();
    }


    @Bean // nie zapomninac o tym xd
    @Override protected UserDetailsService userDetailsService() {

        /** Zad 9 */
//        UserDetails user = User.withUsername("user")
//                .password(encoder().encode("user"))
//                .roles("user")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(encoder().encode("admin"))
//                .roles("admin")
//                .build();

        /** Zad 15 */
        UserDetails user = User.withUsername("user")
                .password(encoder().encode("user"))
                .authorities(UserPermission.USER_READ.name(), UserPermission.USER_EDIT.name())
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(encoder().encode("admin"))
                .authorities(UserPermission.ADMIN.name())
                .build();

        UserDetails spectator = User.withUsername("spectator")
                .password(encoder().encode("spectator"))
                .authorities(UserPermission.USER_READ.name())
                .build();


        return new InMemoryUserDetailsManager(
                user,
                admin
        );
    }

    @Bean public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }

}
