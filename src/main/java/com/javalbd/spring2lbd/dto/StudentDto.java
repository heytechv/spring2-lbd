package com.javalbd.spring2lbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javalbd.spring2lbd.component.SchoolSubject;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

    private Long id;
    @NotNull(message = "Field 'name' cannot be null") private String name;
    @NotNull private String surname;
    @NotNull private Integer age;
    private List<SchoolSubject> schoolSubjectList;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public List<SchoolSubject> getSubjectList() { return schoolSubjectList; }
    public void setSubjectList(List<SchoolSubject> schoolSubjectList) { this.schoolSubjectList = schoolSubjectList; }

}
