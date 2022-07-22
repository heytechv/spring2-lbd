package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import com.javalbd.spring2lbd.dto.UserDto;
import com.javalbd.spring2lbd.exception.UserNotFoundException;
import com.javalbd.spring2lbd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired UserService userService;


    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @GetMapping("/getuser")
    public UserDto getUserByUsername(@RequestParam("username") String username) throws UserNotFoundException {
        return userService.findByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @DeleteMapping("/deleteuser")
    public void deleteUserByUsername(@RequestParam("username") String username) throws UserNotFoundException {
        userService.deleteByUsername(username);
    }

    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @PutMapping("/updatepassword")
    public void updatePasswordByUsername(@RequestBody UpdatePassUserDto passUserDto) throws UserNotFoundException {
        userService.updatePassword(passUserDto);
    }

}
