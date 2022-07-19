package com.javalbd.spring2lbd.apierror;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://www.toptal.com/java/spring-boot-rest-api-error-handling">...</a> */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSingleError> singleErrors;


    public ApiErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiErrorResponse(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }


    public HttpStatus getStatus() { return status; }
    public void setStatus(HttpStatus status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getDebugMessage() { return debugMessage; }
    public void setDebugMessage(String debugMessage) { this.debugMessage = debugMessage; }

    public List<ApiSingleError> getSingleErrors() { return singleErrors; }
    private void addSingleError(ApiSingleError apiSingleError) {
        if (singleErrors == null)
            singleErrors = new ArrayList<>();

        singleErrors.add(apiSingleError);
    }

    public void addValidationError(String object, String message) {
        addSingleError(new ApiValidationError(object, message));
    }

    public void addValidationError(String object, String field, Object rejectedValue, String message) {
        addSingleError(new ApiValidationError(object, field, rejectedValue, message));
    }

    public void addValidationError(ObjectError objectError) {
        if (objectError == null)
            return;
        addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    public void addValidationError(FieldError fieldError) {
        if (fieldError == null)
            return;
        addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
    }

    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationError);
    }

    public ResponseEntity<Object> buildResponseEntity() {
        return ResponseEntity.status(this.getStatus()).header("successful", "false").body(this);
    }

}
