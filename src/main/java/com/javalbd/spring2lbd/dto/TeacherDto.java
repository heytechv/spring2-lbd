package com.javalbd.spring2lbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javalbd.spring2lbd.component.SchoolSubject;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherDto {

    private Long id;
    @NotNull private String name;
    @NotNull private String surname;
    @NotNull private SchoolSubject schoolSubject;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public SchoolSubject getSubject() { return schoolSubject; }
    public void setSubject(SchoolSubject schoolSubject) { this.schoolSubject = schoolSubject; }

}
