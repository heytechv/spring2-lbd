package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.UpdatePassUserDto;
import com.javalbd.spring2lbd.dto.UserDto;
import com.javalbd.spring2lbd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired UserService userService;


    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @GetMapping("/getuser")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam("username") String username) {
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @DeleteMapping("/deleteuser")
    public ResponseEntity<String> deleteUserByUsername(@RequestParam("username") String username) {
        userService.deleteByUsername(username);

        return ResponseEntity.ok("User deleted");
    }

    @PreAuthorize("hasAnyAuthority('ACCESS_ALL')")
    @PutMapping("/updatepassword")
    public ResponseEntity<String> updatePasswordByUsername(@RequestBody UpdatePassUserDto passUserDto) {
        userService.updatePassword(passUserDto);

        return ResponseEntity.ok("Password updated");
    }

}
