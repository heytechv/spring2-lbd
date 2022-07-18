package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    List<Student> studentList = new ArrayList<>();

    @Override public Student findById(Long id) {
        for (Student student : studentList)
            if (Objects.equals(student.getId(), id))
                return student;
        return null;
    }

    @Override public List<Student> findAll() {
        return studentList;
    }

    @Override public void addStudent(Student student) {
        studentList.add(student);
    }

    @Override public void addStudent(String name, String surname, Integer age) {
        Student student = new Student();
        student.setId((long)studentList.size());
        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);

        addStudent(student);
    }

    @Override public void addStudent(String name, String surname, Integer age, SchoolSubject... schoolSubjectList) {
        Student student = new Student();
        student.setId((long)studentList.size());
        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);
        student.setSubjectList(new ArrayList<>(Arrays.asList(schoolSubjectList)));

        addStudent(student);
    }

    @Override public List<StudentDto> getAllStudents() {
        return studentList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override public StudentDto getStudent(Long id) {
        Student student = findById(id);
        if (student == null)
            return null;

        return convertToDto(student);
    }

    @Override public void editStudent(Long id, String surname, Integer age) {
        Student student = findById(id);

        // TODO zabezpieczenia/komunikaty
        if (student == null)
            return;

        student.setSurname(surname);
        student.setAge(age);
    }

    @Override public void deleteStudent(Long id) {
        studentList.remove(findById(id));
    }

    @Override public void assignToSubject(Long studentId, SchoolSubject subject) {
        Student student = findById(studentId);
        if (student == null)
            return;

        student.addSubject(subject);
    }

    @Override public void removeFromSubject(Long studentId, SchoolSubject subject) {
        Student student = findById(studentId);
        if (student == null)
            return;

        student.removeSubject(subject);
    }

    /** ------------------------------------------------------------------------------------ **
    /** -- Mapper -------------------------------------------------------------------------- **
    /** ------------------------------------------------------------------------------------ **/
    public StudentDto convertToDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(student.getId());
        studentDto.setName(student.getName());
        studentDto.setSurname(student.getSurname());
        studentDto.setAge(student.getAge());
        studentDto.setSubjectList(student.getSubjectList());

        return studentDto;
    }


}
