package com.javalbd.spring2lbd.validator;

import org.springframework.beans.BeanWrapperImpl;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullAtLeastOneValidator implements ConstraintValidator<NotNullAtLeastOne, Object> {

    private String[] fieldNames;

    @Override public void initialize(NotNullAtLeastOne constraintAnnotation) {
        this.fieldNames = constraintAnnotation.fieldNames();
    }

    @Override public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        // Cos sie namieszalo
        if (object == null)
            throw new IllegalArgumentException("@NotNullAtLeastOne requires valid object");

        // Bierzemy obiekt
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);

        // Iterujemy (czy jakis jest uzupelniony)
        for (String fieldName : fieldNames) {

            // Czy pole podane w @NotNullAtLeastOne istnieje
            if (!beanWrapper.isReadableProperty(fieldName))
                throw new IllegalArgumentException("@NotNullAtLeastOne requires valid field");;
            // Wartosc
            Object fieldValue = beanWrapper.getPropertyValue(fieldName);
            // jak null to isValid nie przeszlo
            if (fieldValue != null)
                return true;

            // Wylaczamy domyslny error (bo pokazuje tylko nazwe klasy a field jest null wiec useless)
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Dodajemy pola ktore brakuja
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName).addConstraintViolation();
        }

        return false;
    }

}
