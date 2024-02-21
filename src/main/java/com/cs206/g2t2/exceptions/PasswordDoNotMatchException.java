package com.cs206.g2t2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class PasswordDoNotMatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordDoNotMatchException() {
        super("Passwords do not match");
    }
}
