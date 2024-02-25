package com.cs206.g2t2.exceptions.unauthorized;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED) // 401 Error
public class InvalidCredentialsException extends UnauthorizedException {

    private static final long serialVersionUID = 1L;

    public InvalidCredentialsException() {
        super("Username or Password is incorrect. Please try again");
    }
}
