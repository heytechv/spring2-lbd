package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.EditStudentDto;
import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.entity.Student;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.MissingRequiredPropertiesException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    List<Student> studentList = new ArrayList<>();

    @Override public Student findById(Long id) {
        for (Student student : studentList)
            if (Objects.equals(student.getId(), id))
                return student;

        throw new EntityNotFoundException("Student not found with id=" + id);
    }

    @Override public List<Student> findAll() {
        return studentList;
    }

    @Override public void addStudent(Student student) {
        if (student.getId() == null)
            student.setId((long) studentList.size());

        studentList.add(student);
    }

    @Override public void addStudent(String name, String surname, Integer age) {
        Student student = new Student();
        student.setName(name);
        student.setSurname(surname);
        student.setAge(age);

        addStudent(student);
    }

    @Override public void addStudent(StudentDto studentDto) {
        addStudent(convertToEntity(studentDto));
    }

    @Override public void addStudent(String name, String surname, Integer age, SchoolSubject... schoolSubjectList) {
        Student student = new Student();
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
        return convertToDto(findById(id));
    }

    @Override public void editStudent(Long id, String surname, Integer age) {
        Student student = findById(id);
        if (surname != null)
            student.setSurname(surname);
        if (age != null)
            student.setAge(age);
    }

    @Override public void editStudent(EditStudentDto editStudentDto) {
        editStudent(editStudentDto.getEditId(), editStudentDto.getSurname(), editStudentDto.getAge());
    }

    @Override public void deleteStudent(Long id) {
        studentList.remove(findById(id));
    }

    @Override public void assignToSubject(Long studentId, SchoolSubject subject) {
        Student student = findById(studentId);
        student.addSubject(subject);
    }

    @Override public void removeFromSubject(Long studentId, SchoolSubject subject) {
        Student student = findById(studentId);
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

    public Student convertToEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setName(studentDto.getName());
        student.setSurname(studentDto.getSurname());
        student.setAge(studentDto.getAge());
        student.setSubjectList(studentDto.getSubjectList());

        return student;
    }


}
