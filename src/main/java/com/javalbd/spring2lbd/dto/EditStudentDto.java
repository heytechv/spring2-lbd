package com.javalbd.spring2lbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.javalbd.spring2lbd.component.SchoolSubject;
import com.javalbd.spring2lbd.validator.NotNullAtLeastOne;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NotNullAtLeastOne(fieldNames = {"surname", "age"})
public class EditStudentDto {

    @NotNull private Long editId;
    private String surname;
    private Integer age;

    public EditStudentDto() { }

    public EditStudentDto (Long editId, String surname, Integer age) {
        this.editId=editId;
        this.surname=surname;
        this.age=age;
    }

    public Long getEditId() { return editId; }
    public void setEditId(Long editId) { this.editId = editId; }

    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

}
