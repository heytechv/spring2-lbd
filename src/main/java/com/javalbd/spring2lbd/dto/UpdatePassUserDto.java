package com.javalbd.spring2lbd.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter @Setter
public class UpdatePassUserDto {

    @NotNull private String username;
    @NotNull private String newPassword;

}
