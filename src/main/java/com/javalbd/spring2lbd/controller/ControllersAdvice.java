package com.javalbd.spring2lbd.controller;


import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

/**
 * <a href="https://www.toptal.com/java/spring-boot-rest-api-error-handling">...</a>
 * <a href="https://reflectoring.io/spring-boot-exception-handling/">...</a> */

@ControllerAdvice
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class ControllersAdvice  {
public class ControllersAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotfound(EntityNotFoundException ex) {
//        return ResponseEntity.badRequest().body(e.getMessage());
        return ResponseEntity.badRequest().header("successful", "false").body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.badRequest().header("successful", "false").body(ex.getMessage());
    }

    /** Dla @Valid */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (ex.getFieldError() != null)
            return ResponseEntity.badRequest().header("successful", "false")
                    .body(ex.getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().header("successful", "false")
                .body(ex.getMessage());
    }


}
