package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.dto.EditStudentDto;
import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/student")
public class StudentController {

    @Autowired private StudentService studentService;

    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getStudentsAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .header("successful", "true")
                .body(studentService.getAllStudents());
    }

    @GetMapping("/getstudent")
    public ResponseEntity<StudentDto> getStudentsAll(@RequestParam("id") Long id) {
        return ResponseEntity.ok()
                .header("successful", "true")
                .body(studentService.getStudent(id));
    }

    @PostMapping("/addstudent")
//    public void addStudent(
//            @RequestParam("name")    String name,
//            @RequestParam("surname") String surname,
//            @RequestParam("age")     Integer age) {
    /** @Valid odnosie sie do @NotNull w StudentDto.class */
    public void addStudent(@Valid @RequestBody StudentDto studentDto) {
//        studentService.addStudent(name, surname, age);
        studentService.addStudent(studentDto);
    }

    @PutMapping("/editstudent")
//    public void editStudent(
//            @RequestParam("id")      Long id,
//            @RequestParam("surname") String surname,
//            @RequestParam("age")     Integer age ) {
    public void editStudent(@Valid @RequestBody EditStudentDto editStudentDto) {
//        studentService.editStudent(id, surname, age);
        studentService.editStudent(editStudentDto);
    }

    @DeleteMapping("/deletestudent")
    public void deleteStudent(@RequestParam("id") Long id) {
        studentService.deleteStudent(id);
    }


}
