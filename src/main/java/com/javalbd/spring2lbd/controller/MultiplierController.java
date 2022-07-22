package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/multiplier")
public class MultiplierController {

    @Autowired MessageService messageService;


//    @PreAuthorize("hasAnyAuthority('MULTIPLIER_WRITE', 'ACCESS_ALL')")
    @PostMapping("/setmultiplier")
    public ResponseEntity<Object> getDecimal(@RequestParam("multiplier") Integer multiplier) {
        messageService.setMultiplier(multiplier);
        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyAuthority('MULTIPLIER_READ', 'ACCESS_ALL')")
    @GetMapping("/getmultiplier")
    public ResponseEntity<Object> getDecimal() {
        ValuesDto valuesDto = new ValuesDto();
        valuesDto.setMultiplier(messageService.getMultiplier());

        return ResponseEntity.ok(valuesDto);
    }

}
