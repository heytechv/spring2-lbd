- [Standard DTO Validation](https://www.javaguides.net/2021/04/spring-boot-dto-validation-example.html)

### Postman
Konfiguracja z Postmana w `docs/Postman`

________

## Filter
Filter happens before ... // TODO

## Interceptor
// TODO

## Best way for throwing Exceptions
Custom `ControllerAdvice.java` + custom `ApiError classes` // TODO

## Custom Spring Boot Annotation for Validation (for @Valid)
In this project i use annonation to insists on object to have at least one valid (not null) field among chosen.<br/><br/>
Useful links:
- [StackOverflow - validate that at least one field is not null](https://stackoverflow.com/questions/12211734/hibernate-validation-annotation-validate-that-at-least-one-field-is-not-null)
- [Creating a Custom Annotation for Validation](https://blog.clairvoyantsoft.com/spring-boot-creating-a-custom-annotation-for-validation-edafbf9a97a4)
- [Spring MVC Custom Validation](https://www.baeldung.com/spring-mvc-custom-validator)
- [Exception - show missing fields](https://stackoverflow.com/questions/69854508/missing-validation-error-message-of-java-constraintvalidator-on-class-level)

### 1. Create interface
*NotNullAtLeasOne.java*
```java
@Target({ElementType.TYPE})     // @NotNullAtLeastOne - oczekuje adnotacji nad klassa (FIELD to by nad polem czyli nad zmienna)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullAtLeastOneValidator.class)     // podpinamy klase
public @interface NotNullAtLeastOne {

    public String message() default "At least one field must be set";   // Error message
    public Class<?>[] groups() default {};                              // Group of constraints
    public Class<? extends Payload>[] payload() default {};             // Additional information about annotation

    public String[] fieldNames();                                       // My fields I want NotNullAtLeastOne

}
```

### 2. Create Handler/Validator for our Interface
*NotNullAtLeastOneValidator.java*
```java
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

        // Iterujemy (czy jakies jest uzupelnione)
        for (String fieldName : fieldNames) {

            // Czy pole podane w @NotNullAtLeastOne(pole) istnieje
            if (!beanWrapper.isReadableProperty(fieldName))
                throw new IllegalArgumentException("@NotNullAtLeastOne requires valid field");;
            // Wartosc
            Object fieldValue = beanWrapper.getPropertyValue(fieldName);
            // jak wszystkie pola null to isValid nie przeszlo
            // jak chociaz jedno nie null to git
            if (fieldValue != null)
                return true;

            // Wylaczamy domyslny error (bo pokazuje tylko nazwe klasy a nazwa field jest null wiec useless)
            constraintValidatorContext.disableDefaultConstraintViolation();
            // Dodajemy pola ktore sa null
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(fieldName).addConstraintViolation();
        }

        // Jak nie przeszlo validacji to zwracamy false, a pola ktore dodalismy do constraintValidatorContext
        // zostana pokazane w bledzie (obsluga bledu w ControllersAdvice.java)
        return false;
    }

}
```

### 3. Use our brand-new annotation
We can use it for example for Dto validation:<br/>
*EditStudentDto.java*
```java
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
```
We want at least one not null value among fields `{surname, age}`.

