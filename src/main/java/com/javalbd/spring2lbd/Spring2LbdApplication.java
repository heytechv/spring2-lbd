package com.javalbd.spring2lbd;

import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Spring2LbdApplication {


    @Autowired private StudentService studentService;

    @PostConstruct
    void init() {
        studentService.addStudent("Maciek", "Macinski", 20);
        studentService.addStudent("Marek", "Markunski", 25);
        studentService.addStudent("Jacek", "Jackunski", 30);
        studentService.addStudent("Robert", "Robercik", 28);
        studentService.addStudent("Andrzej", "Suma", 20);
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring2LbdApplication.class, args);
    }

}
