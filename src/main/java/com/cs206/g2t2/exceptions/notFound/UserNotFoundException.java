package com.cs206.g2t2.exceptions.notFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class UserNotFoundException extends NotFoundException {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String userId) {
        super("User with userId " + userId + " does not exist");
    }
}
