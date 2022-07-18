package com.javalbd.spring2lbd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Teacher {

    @Id private Long id;
    private String name;
    private String surname;
    private Subject subject;




    enum Subject {
        ALGEBRA, LAW, BIOLOGY
    }

}
