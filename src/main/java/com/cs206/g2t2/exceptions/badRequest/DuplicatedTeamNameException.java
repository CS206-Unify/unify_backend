package com.cs206.g2t2.exceptions.badRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 400 Error
public class DuplicatedTeamNameException extends BadRequestException {

    private static final long serialVersionUID = 1L;

    public DuplicatedTeamNameException(String teamName) {
        super("Team Name " + teamName + " exists");
    }
}
