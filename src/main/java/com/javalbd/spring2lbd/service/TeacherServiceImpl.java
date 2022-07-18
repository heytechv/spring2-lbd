package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.dto.TeacherDto;
import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired private StudentService studentService;

    List<Teacher> teacherList = new ArrayList<>();


    private Teacher findById(Long id) {
        for (Teacher teacher : teacherList)
            if (Objects.equals(teacher.getId(), id))
                return teacher;

        throw new EntityNotFoundException("Teacher not found with id=" + id);
    }

    @Override public List<TeacherDto> getAllTeachers() {
        return teacherList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override public TeacherDto getTeacher(Long id) {
        return convertToDto(findById(id));
    }

    @Override public SchoolSubject getTeacherClass(Long id) {
        return findById(id).getSubject();
    }

    @Override public void addTeacher(Teacher teacher) {
        teacherList.add(teacher);
    }

    @Override public void addTeacher(String name, String surname, SchoolSubject subject) {
        Teacher teacher = new Teacher();
        teacher.setId((long)teacherList.size());
        teacher.setName(name);
        teacher.setSurname(surname);
        teacher.setSubject(subject);

        addTeacher(teacher);
    }

    @Override public void addTeacher(TeacherDto teacherDto) {
        addTeacher(convertToEntity(teacherDto));
    }

    @Override public void deleteTeacher(Long id) {
        Teacher teacher = findById(id);
        teacherList.remove(teacher);
    }

    @Override public void deleteStudentFromClassByTeacherId(Long teacherId, Long studentId) {
        SchoolSubject teachersSubject = getTeacherClass(teacherId);
        if (teachersSubject == null)
            return;

        studentService.removeFromSubject(studentId, teachersSubject);
    }


    /** ------------------------------------------------------------------------------------ **
     /** -- Mapper -------------------------------------------------------------------------- **
     /** ------------------------------------------------------------------------------------ **/
    public TeacherDto convertToDto(Teacher teacher) {
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(teacher.getId());
        teacherDto.setName(teacher.getName());
        teacherDto.setSurname(teacher.getSurname());
        teacherDto.setSubject(teacher.getSubject());
        return teacherDto;
    }

    public Teacher convertToEntity(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherDto.getId());
        teacher.setName(teacherDto.getName());
        teacher.setSurname(teacherDto.getSurname());
        teacher.setSubject(teacherDto.getSubject());
        return teacher;

    }
}
