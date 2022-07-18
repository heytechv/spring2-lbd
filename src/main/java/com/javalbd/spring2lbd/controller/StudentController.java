package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

    @Autowired private StudentService studentService;

    @GetMapping("/all")
    public List<StudentDto> getStudentsAll() {
        return studentService.getAllStudents();
    }

    @PostMapping("/addstudent")
    public void addStudent(
            @RequestParam("name")    String name,
            @RequestParam("surname") String surname,
            @RequestParam("age")     Integer age) {

        studentService.addStudent(name, surname, age);
    }

    @PutMapping("/editstudent")
    public void editStudent(
            @RequestParam("id")      Long id,
            @RequestParam("surname") String surname,
            @RequestParam("age")     Integer age ) {

        studentService.editStudent(id, surname, age);
    }

    @DeleteMapping("/deletestudent")
    public void deleteStudent(@RequestParam("id") Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping("/getstudent")
    public StudentDto getStudentsAll(@RequestParam("id") Long id) {
        return studentService.getStudent(id);
    }

}