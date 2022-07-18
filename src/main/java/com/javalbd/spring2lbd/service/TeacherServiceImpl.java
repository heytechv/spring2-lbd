package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.dto.StudentDto;
import com.javalbd.spring2lbd.dto.TeacherDto;
import com.javalbd.spring2lbd.entity.Student;
import com.javalbd.spring2lbd.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return null;
    }

    @Override public List<TeacherDto> getAllTeachers() {
        return teacherList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override public TeacherDto getTeacher(Long id) {
        Teacher teacher = findById(id);

        // TODO zabezpieczenia
        if (teacher == null)
            return null;

        return convertToDto(teacher);
    }

    @Override public SchoolSubject getTeacherClass(Long id) {
        Teacher teacher = findById(id);

        if (teacher == null)
            return null;

        return teacher.getSubject();
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

    @Override public void deleteTeacher(Long id) {
        Teacher teacher = findById(id);

        if (teacher == null)
            return;

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
}
