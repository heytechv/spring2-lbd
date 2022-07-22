package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.ValuesDto;
import com.javalbd.spring2lbd.security.PermissionType;
import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/decimal")
public class DecimalController {

    @Autowired MessageService messageService;


//    @PreAuthorize("hasAnyAuthority('DECIMAL_WRITE', 'ACCESS_ALL')")
    @PostMapping("/setdecimal")
    public ResponseEntity<Object> getDecimal(@RequestParam("decimalPlaces") Integer decimalPlaces) {
        messageService.setDecimalPlaces(decimalPlaces);
        return ResponseEntity.ok("ok");
    }

    @PreAuthorize("hasAnyAuthority('DECIMAL_READ', 'ACCESS_ALL')")
    @GetMapping("/getdecimal")
    public ResponseEntity<Object> getDecimal() {
        ValuesDto valuesDto = new ValuesDto();
        valuesDto.setDecimalPlaces(messageService.getDecimalPlaces());

        return ResponseEntity.ok(valuesDto);
    }

}
