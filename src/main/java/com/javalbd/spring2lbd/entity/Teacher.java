package com.javalbd.spring2lbd.entity;

import com.javalbd.spring2lbd.component.SchoolSubject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

//@Entity
public class Teacher {

    private Long id;
    private String name;
    private String surname;
    private SchoolSubject subject;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public SchoolSubject getSubject() { return subject; }
    public void setSubject(SchoolSubject subject) { this.subject = subject; }




}
