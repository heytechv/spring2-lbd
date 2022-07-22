package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import com.javalbd.spring2lbd.dto.UserDto;
import com.javalbd.spring2lbd.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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

    public UserDto findByUsername(String username) throws UserNotFoundException {
        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        if (userDetails == null)
            throw new UserNotFoundException();

        return new UserDto(userDetails.getUsername(), userDetails.getPassword(), Collections.singletonList(userDetails.getAuthorities()));
    }

    public void deleteByUsername(String username) throws UserNotFoundException {
        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        if (userDetails == null)
            throw new UserNotFoundException();

        inMemoryUserDetailsManager.deleteUser(userDetails.getUsername());
    }

    public void updatePassword(String username, String newPassword) throws UserNotFoundException {
        UserDetails userDetails = inMemoryUserDetailsManager.loadUserByUsername(username);
        if (userDetails == null)
            throw new UserNotFoundException();

        inMemoryUserDetailsManager.updatePassword(userDetails, passwordEncoder.encode(newPassword));
    }

    public void updatePassword(UpdatePassUserDto passUserDto) throws UserNotFoundException {
        updatePassword(passUserDto.getUsername(), passUserDto.getNewPassword());
    }

}
