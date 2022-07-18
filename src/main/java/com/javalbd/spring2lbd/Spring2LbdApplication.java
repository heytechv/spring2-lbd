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
        studentService.addStudent("St Maciek" , "St Macinski" , 20);
        studentService.addStudent("St Marek"  , "St Markunski", 25);
        studentService.addStudent("St Jacek"  , "St Jackunski", 30);
        studentService.addStudent("St Robert" , "St Robercik" , 28);
        studentService.addStudent("St Andrzej", "St Suma"     , 20);

        studentService.addStudent("St Maciek2" , "St Macinski" , 20, SchoolSubject.ALGEBRA, SchoolSubject.LAW);
        studentService.addStudent("St Marek2"  , "St Markunski", 25, SchoolSubject.BIOLOGY);
        studentService.addStudent("St Jacek2"  , "St Jackunski", 30, SchoolSubject.ALGEBRA);
        studentService.addStudent("St Robert2" , "St Robercik" , 28, SchoolSubject.BIOLOGY);
        studentService.addStudent("St Andrzej2", "St Suma"     , 20, SchoolSubject.LAW, SchoolSubject.BIOLOGY);

        /** Teachers x3 */
        teacherService.addTeacher("Te Marcin"   , "Te Siema"     , SchoolSubject.LAW);
        teacherService.addTeacher("Te Agnieszka", "Te Dowidzenia", SchoolSubject.BIOLOGY);
        teacherService.addTeacher("Te Patrycja" , "Te Przyst"    , SchoolSubject.ALGEBRA);
    }

    public static void main(String[] args) {
        SpringApplication.run(Spring2LbdApplication.class, args);
    }

}
