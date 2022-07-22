package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.security.PermissionType;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/decimal")
public class DecimalController {

    @Autowired MessageService messageService;


//    @PreAuthorize("hasAnyAuthority('DECIMAL_WRITE', 'ACCESS_ALL')")
    @PostMapping("/setdecimal")
    public void getDecimal(@RequestParam("decimalPlaces") Integer decimalPlaces) {
        messageService.setDecimalPlaces(decimalPlaces);
    }

    @PreAuthorize("hasAnyAuthority('DECIMAL_READ', 'ACCESS_ALL')")
    @GetMapping("/getdecimal")
    public Integer getDecimal() {
        return messageService.getDecimalPlaces();
    }

}
