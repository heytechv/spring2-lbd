package com.javalbd.spring2lbd.controller;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.TeacherDto;
import com.javalbd.spring2lbd.entity.Teacher;
import com.javalbd.spring2lbd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired private TeacherService teacherService;


    @GetMapping("/all")
    public ResponseEntity<List<TeacherDto>> getTeacherAll() {
        return ResponseEntity.ok()
                .header("successful", "true")
                .body(teacherService.getAllTeachers());
    }

    @GetMapping("/getteacher")
    public ResponseEntity<TeacherDto> getTeacher(@RequestParam("id") Long id) {
        return ResponseEntity.ok()
                .header("successful", "true")
                .body(teacherService.getTeacher(id));
    }

    @GetMapping("/getteacherclass")
    public ResponseEntity<SchoolSubject> getTeacherClass(@RequestParam("id") Long id) {
        return ResponseEntity.ok()
                .header("successful", "true")
                .body(teacherService.getTeacherClass(id));
    }

    @PostMapping("/addteacher")
//    public void addTeacher(
//            @RequestParam("name") String name,
//            @RequestParam("surname") String surname,
//            @RequestParam("subject") SchoolSubject subject) {
    public void addTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.addTeacher(teacherDto);
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
