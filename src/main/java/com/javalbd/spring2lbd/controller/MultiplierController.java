package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/multiplier")
public class MultiplierController {

    @Autowired MessageService messageService;


    @PutMapping("/setmultiplier")
    public void getDecimal(@RequestParam("multiplier") Integer multiplier) {
        messageService.setMultiplier(multiplier);
    }

    @GetMapping("/getmultiplier")
    public Integer getDecimal() {
        return messageService.getMultiplier();
    }

}
