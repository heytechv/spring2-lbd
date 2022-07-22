package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.CreateUserDto;
import com.javalbd.spring2lbd.exception.UserAlreadyExistsException;
import com.javalbd.spring2lbd.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired RegisterService registerService;


    @PostMapping("/register")
    public void register(@Valid @RequestBody CreateUserDto createUserDto) throws UserAlreadyExistsException {
        registerService.createUser(createUserDto);
    }

}
