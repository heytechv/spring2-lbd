package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/multiplier")
public class MultiplierController {

    @Autowired MessageService messageService;


//    @PreAuthorize("hasAnyAuthority('MULTIPLIER_WRITE', 'ACCESS_ALL')")
    @PostMapping("/setmultiplier")
    public void getDecimal(@RequestParam("multiplier") Integer multiplier) {
        messageService.setMultiplier(multiplier);
    }

    @PreAuthorize("hasAnyAuthority('MULTIPLIER_READ', 'ACCESS_ALL')")
    @GetMapping("/getmultiplier")
    public Integer getDecimal() {
        return messageService.getMultiplier();
    }

}
