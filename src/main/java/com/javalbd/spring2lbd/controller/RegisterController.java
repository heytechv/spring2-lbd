package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.CreateUserDto;
import com.javalbd.spring2lbd.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegisterController {

    @Autowired RegisterService registerService;


    @PostMapping("/register")
    public void register(@Valid @RequestBody CreateUserDto createUserDto) {
        registerService.createUser(createUserDto);
    }

}
