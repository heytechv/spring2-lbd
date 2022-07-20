package com.javalbd.spring2lbd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
//@PreAuthorize("hasRole('admin')")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

//    @PreAuthorize("hasAnyRole('user', 'admin')")
    @GetMapping("/getadmin")
    public String getAdmin() {
        return "This is admin!";
    }

//    @PreAuthorize("hasRole('admin')")
    @PostMapping("/createuser")
    public String createUser() {
        return "User created!";
    }

//    @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/deleteuser")
    public String deleteUser() {
        return "User deleted!";
    }

}
