package com.javalbd.spring2lbd.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter @Setter
public class ValuesDto {

    private Double valueOne;
    private Double valueTwo;
    private Integer decimalPlaces;
    private Integer multiplier;


}
