package com.javalbd.spring2lbd.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Insists on object to have at least one valid (not null) field among chosen.
 * <a href="https://blog.clairvoyantsoft.com/spring-boot-creating-a-custom-annotation-for-validation-edafbf9a97a4">...</a>
 * <a href="https://stackoverflow.com/questions/12211734/hibernate-validation-annotation-validate-that-at-least-one-field-is-not-null">...</a>
 * <a href="https://www.baeldung.com/spring-mvc-custom-validator">...</a>
 * <a href="https://stackoverflow.com/questions/69854508/missing-validation-error-message-of-java-constraintvalidator-on-class-level">How to show which fields are missing</a>
 * */
@Target({ElementType.TYPE})     // @NotNullAtLeastOne - oczekuje adnotacji nad klassa (FIELD to by nad polem czyli nad zmienna)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAtLeastOneValidator.class)     // podpinamy klase
public @interface NotNullAtLeastOne {

    public String message() default "At least one field must be set";   // Error message
    public Class<?>[] groups() default {};                              // Group of constraints
    public Class<? extends Payload>[] payload() default {};             // Additional information about annotation

    public String[] fieldNames();                                       // My fields I want NotNullAtLeastOne

}
