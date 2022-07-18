package com.javalbd.spring2lbd.entity;

import com.javalbd.spring2lbd.component.SchoolSubject;

import java.util.ArrayList;
import java.util.List;

//@Entity
//@Table("Student")
public class Student {

//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private List<SchoolSubject> subjectList = new ArrayList<>();

    public Student() {}
    public Student(String name, String surname, Integer age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public List<SchoolSubject> getSubjectList() { return subjectList; }
    public void setSubjectList(List<SchoolSubject> schoolSubjectList) { this.subjectList = schoolSubjectList; }
    public void addSubject(SchoolSubject subject) { subjectList.add(subject); }
    public void removeSubject(SchoolSubject subject) { subjectList.remove(subject); }

}
