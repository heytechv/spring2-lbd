package com.javalbd.spring2lbd.exception;

import javax.naming.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException() {
        super("User not found!");
    }

}
