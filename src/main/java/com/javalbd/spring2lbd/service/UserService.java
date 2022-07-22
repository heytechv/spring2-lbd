package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import com.javalbd.spring2lbd.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService {

    InMemoryUserDetailsManager inMemoryUserDetailsManager;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(InMemoryUserDetailsManager inMemoryUserDetailsManager, PasswordEncoder passwordEncoder) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean doesUsernameExists(String username) {
        return inMemoryUserDetailsManager.userExists(username);
    }

    public UserDto findByUsername(String username) {
        if (!doesUsernameExists(username))
            throw new UsernameNotFoundException("Username '" + username + "' not found!");

        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        return new UserDto(userDetails.getUsername(), userDetails.getPassword(), Collections.singletonList(userDetails.getAuthorities()));
    }

    public void deleteByUsername(String username) {
        if (!doesUsernameExists(username))
            throw new UsernameNotFoundException("Username '" + username + "' not found!");

        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        inMemoryUserDetailsManager.deleteUser(userDetails.getUsername());
    }

    public void updatePassword(String username, String newPassword) {
        if (!doesUsernameExists(username))
            throw new UsernameNotFoundException("Username '" + username + "' not found!");

        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        inMemoryUserDetailsManager.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }

    public void updatePassword(UpdatePassUserDto passUserDto) {
        updatePassword(passUserDto.getUsername(), passUserDto.getNewPassword());
    }

}
