package com.javalbd.spring2lbd;

import com.javalbd.spring2lbd.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Spring2LbdApplication {

    @Autowired MessageService messageService;

    @PostConstruct
    public void init() {
        messageService.getValues();

    }

    public static void main(String[] args) {
        SpringApplication.run(Spring2LbdApplication.class, args);
    }

}

