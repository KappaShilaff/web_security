package com.example.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {
    public UserExistsException(String username) {
        super("User already exists. username: " + username);
    }
}