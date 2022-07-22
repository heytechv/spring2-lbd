package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/number")
public class NumberController {

    @Autowired MessageService messageService;


    @GetMapping("/getcalculatedvalues")
    public ValuesDto getCalculatedValues() {
        return messageService.getCalculatedValues();
    }

}
