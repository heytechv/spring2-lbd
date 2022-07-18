package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.TeacherDto;
import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.entity.Teacher;

import java.util.List;

public interface TeacherService {

    List<TeacherDto> getAllTeachers();
    TeacherDto getTeacher(Long id);
    SchoolSubject getTeacherClass(Long id);

    void addTeacher(Teacher teacher);
    void addTeacher(TeacherDto teacherDto);
    void addTeacher(String name, String surname, SchoolSubject subject);

    void deleteTeacher(Long id);

    void deleteStudentFromClassByTeacherId(Long teacherId, Long studentId);


}
