package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();
    StudentDto getStudent(Long id);

    void addStudent(Student student);
    void addStudent(String name, String surname, Integer age);

    void editStudent(Long id, String surname, Integer age);
    void deleteStudent(Long id);


}
