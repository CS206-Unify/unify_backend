package com.cs206.g2t2.controller;

import com.cs206.g2t2.exceptions.badRequest.BadRequestException;
import com.cs206.g2t2.exceptions.brawlStarsApi.ExternalAPIException;
import com.cs206.g2t2.exceptions.notFound.NotFoundException;
import com.cs206.g2t2.exceptions.unauthorized.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private Map<String, Object> returnMapFromException(Exception exception, HttpStatusCode status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", exception.getClass());
        body.put("message", exception.getMessage());
        return body;
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException,
                                                               HttpStatusCode status) {
        Map<String, Object> body = returnMapFromException(badRequestException, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException unauthorizedException,
                                                                 HttpStatusCode status) {
        Map<String, Object> body = returnMapFromException(unauthorizedException, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException,
                                                              HttpStatusCode status) {
        Map<String, Object> body = returnMapFromException(notFoundException, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }

    @ExceptionHandler(ExternalAPIException.class)
    protected ResponseEntity<Object> handleApiServerException(ExternalAPIException apiServerException) {
        HttpStatusCode status = HttpStatus.valueOf(apiServerException.getStatusCode());
        Map<String, Object> body = returnMapFromException(apiServerException, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }
}