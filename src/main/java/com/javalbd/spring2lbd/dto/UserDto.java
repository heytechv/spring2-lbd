package com.javalbd.spring2lbd.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter @Getter
public class UserDto {

    private String username;
    private String password;
    private List authorities;


    public UserDto() { }

    public UserDto(String username, String password, List authorities) {
        this.username=username;
        this.password=password;
        this.authorities=authorities;
    }

}
