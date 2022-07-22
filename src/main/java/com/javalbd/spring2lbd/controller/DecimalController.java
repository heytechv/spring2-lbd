package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/decimal")
public class DecimalController {

    @Autowired MessageService messageService;


    @PostMapping("/setdecimal")
    public void getDecimal(@RequestParam("decimalPlaces") Integer decimalPlaces) {
        messageService.setDecimalPlaces(decimalPlaces);
    }

    @GetMapping("/getdecimal")
    public Integer getDecimal() {
        return messageService.getDecimalPlaces();
    }

}
