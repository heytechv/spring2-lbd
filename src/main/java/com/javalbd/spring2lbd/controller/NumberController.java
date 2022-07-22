package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/number")
public class NumberController {

    @Autowired MessageService messageService;


    @GetMapping("/getcalculatedvalues")
    public ResponseEntity<ValuesDto> getCalculatedValues() {
        return ResponseEntity.ok(messageService.getCalculatedValues());
    }

}
