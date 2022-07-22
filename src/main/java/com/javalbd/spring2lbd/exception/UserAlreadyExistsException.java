package com.javalbd.spring2lbd.exception;

import javax.naming.AuthenticationException;

public class UserAlreadyExistsException extends AuthenticationException {

    public UserAlreadyExistsException() {
        super("User already exists!");
    }

}
