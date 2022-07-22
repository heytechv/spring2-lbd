package com.javalbd.spring2lbd.service;

import com.javalbd.spring2lbd.dto.ValuesDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MessageService {

    @Getter @Setter private Double valueOne;
    @Getter @Setter private Double valueTwo;
    @Getter @Setter private Integer decimalPlaces;
    @Getter @Setter private Integer multiplier;


    private Double rob;

    public MessageService() {
        this.decimalPlaces = 2;
        this.multiplier = 1;
    }

    private void calculateValues() {
        var p=0; var q=1;
        valueOne = p + new Random().nextDouble()* (q-p+1);
        rob = valueOne;
        valueTwo = p + new Random().nextDouble()* (q-p+1);

        valueOne *= multiplier;
        valueTwo *= multiplier;

        double mul = Math.pow(10, decimalPlaces);
        valueOne = Math.floor(valueOne*mul) / mul;
        valueTwo = Math.floor(valueTwo*mul) / mul;
    }

    public ValuesDto getValues() {
        calculateValues();
        return convertThisToDto();
    }

    public ValuesDto getCalculatedValues() {
        calculateValues();
        ValuesDto valuesDto = new ValuesDto();
        valuesDto.setValueOne(valueOne);
        valuesDto.setValueTwo(valueTwo);

        return valuesDto;
    }


    public ValuesDto convertThisToDto() {
        ValuesDto valuesDto = new ValuesDto();
        valuesDto.setValueOne(valueOne);
        valuesDto.setValueTwo(valueTwo);
        valuesDto.setDecimalPlaces(decimalPlaces);
        valuesDto.setMultiplier(multiplier);

        return valuesDto;
    }


}


