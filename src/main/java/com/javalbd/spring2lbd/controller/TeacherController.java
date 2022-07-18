package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.TeacherDto;
import com.javalbd.spring2lbd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired private TeacherService teacherService;


    @GetMapping("/all")
    public List<TeacherDto> getTeacherAll() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/getteacher")
    public TeacherDto getTeacher(@RequestParam("id") Long id) {
        return teacherService.getTeacher(id);
    }

    @GetMapping("/getteacherclass")
    public SchoolSubject getTeacherClass(@RequestParam("id") Long id) {
        return teacherService.getTeacherClass(id);
    }

    @PostMapping("/addteacher")
    public void addTeacher(
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("subject") SchoolSubject subject) {

        teacherService.addTeacher(name, surname, subject);
    }

    @DeleteMapping("/deleteteacher")
    public void deleteTeacher(@RequestParam("id") Long id) {
        teacherService.deleteTeacher(id);
    }

    @DeleteMapping("/deletestudentfromclass")
    public void deleteStudentFromClass(
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("studentId") Long studentId) {

        teacherService.deleteStudentFromClassByTeacherId(teacherId, studentId);
    }


}
