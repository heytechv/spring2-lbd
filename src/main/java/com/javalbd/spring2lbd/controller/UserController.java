package com.javalbd.spring2lbd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
//@PreAuthorize("hasAnyRole('user', 'admin')")
public class UserController {

    @PreAuthorize("hasAnyAuthority('USER_READ', 'ADMIN')")
    @GetMapping("/getuser")
    public String getUser() {
        return "This is user!";
    }

//    @PreAuthorize("hasAnyRole('user', 'admin')")
//    @PreAuthorize("hasAnyAuthority('USER_EDIT', 'ADMIN')")
    @PutMapping("/updateuser")
    public String updateUser() {
        return "User updated!";
    }

}
