package com.cs206.g2t2.exceptions.notFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class UsernameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {
        super("User with username " + username + " does not exist");
    }
}
