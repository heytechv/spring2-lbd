package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    PasswordEncoder passwordEncoder;
    InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired public RegisterService(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }


    public void createUser(String username, String password, String[] authorities) {
        if (inMemoryUserDetailsManager.userExists(username))
            throw new UsernameNotFoundException("Username '" + username + "' not found!");

        UserDetails userDetails = User.withUsername(username)
                .password(passwordEncoder.encode(password))
                .authorities(authorities)
                .build();


        inMemoryUserDetailsManager.createUser(userDetails);
    }

    public void createUser(CreateUserDto createUserDto) {
        createUser(createUserDto.getUsername(), createUserDto.getPassword(), createUserDto.getAuthorities());
    }



}
