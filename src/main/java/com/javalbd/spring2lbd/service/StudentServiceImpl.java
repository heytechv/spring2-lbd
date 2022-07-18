package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    List<Student> studentList = new ArrayList<>();

    private Student findById(Long id) {
        for (Student student : studentList)
            if (Objects.equals(student.getId(), id))
                return student;
        return null;
    }



    public void addStudent(Student student) {
        studentList.add(student);
    }

    public void addStudent(String name, String surname, Integer age) {
        Student student = new Student();
        student.setId((long)studentList.size());
        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);

        addStudent(student);
    }

    public List<StudentDto> getAllStudents() {
        return studentList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public StudentDto getStudent(Long id) {
        Student student = findById(id);
        if (student == null)
            return null;

        return convertToDto(student);
    }

    public void editStudent(Long id, String surname, Integer age) {
        Student student = findById(id);
        if (student == null)
            return;

        student.setSurname(surname);
        student.setAge(age);
    }

    public void deleteStudent(Long id) {
        studentList.remove(findById(id));
    }

    public StudentDto convertToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setAge(student.getAge());
        return studentDto;
    }





}
