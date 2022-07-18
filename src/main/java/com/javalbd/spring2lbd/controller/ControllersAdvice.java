package com.javalbd.spring2lbd.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * <a href="https://www.toptal.com/java/spring-boot-rest-api-error-handling">...</a>
 * <a href="https://reflectoring.io/spring-boot-exception-handling/">...</a> */

@ControllerAdvice
public class ControllersAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotfound(EntityNotFoundException e) {
//        return ResponseEntity.badRequest().body(e.getMessage());

        /** (do Zad 16) */
        return ResponseEntity.badRequest().header("successful", "false").body(e.getMessage());
    }


}
