package com.javalbd.spring2lbd;

import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.service.StudentService;
import com.javalbd.spring2lbd.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Spring2LbdApplication {

    @Autowired private StudentService studentService;
    @Autowired private TeacherService teacherService;


    @PostConstruct
    void init() {
        /** Students x10 */
        studentService.addStudent("Maciek" , "Macinski" , 20);
        studentService.addStudent("Marek"  , "Markunski", 25);
        studentService.addStudent("Jacek"  , "Jackunski", 30);
        studentService.addStudent("Robert" , "Robercik" , 28);
        studentService.addStudent("Andrzej", "Suma"     , 20);

        studentService.addStudent("Maciek2" , "Macinski" , 20, SchoolSubject.ALGEBRA, SchoolSubject.LAW);
        studentService.addStudent("Marek2"  , "Markunski", 25, SchoolSubject.BIOLOGY);
        studentService.addStudent("Jacek2"  , "Jackunski", 30, SchoolSubject.ALGEBRA);
        studentService.addStudent("Robert2" , "Robercik" , 28, SchoolSubject.BIOLOGY);
        studentService.addStudent("Andrzej2", "Suma"     , 20, SchoolSubject.LAW, SchoolSubject.BIOLOGY);

        /** Teachers x3 */
        teacherService.addTeacher("Marcin"   , "Siema"     , SchoolSubject.LAW);
        teacherService.addTeacher("Agnieszka", "Dowidzenia", SchoolSubject.BIOLOGY);
        teacherService.addTeacher("Patrycja" , "Przyst"    , SchoolSubject.ALGEBRA);
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring2LbdApplication.class, args);
    }

}
