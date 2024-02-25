package com.cs206.g2t2.controller;

import com.cs206.g2t2.exceptions.badRequest.BadRequestException;
import com.cs206.g2t2.exceptions.brawlStarsApi.ExternalAPIException;
import com.cs206.g2t2.exceptions.notFound.NotFoundException;
import com.cs206.g2t2.exceptions.unauthorized.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
    protected ResponseEntity<Object> handleExternalApiException(ExternalAPIException externalAPIException) {
        HttpStatusCode status = HttpStatus.valueOf(externalAPIException.getStatusCode());
        Map<String, Object> body = returnMapFromException(externalAPIException, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }

    @Override
    protected ResponseEntity<Object>
    handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                 HttpHeaders headers,
                                 HttpStatusCode status,
                                 WebRequest request) {

        Map<String, Object> body = returnMapFromException(ex, status);
        return ResponseEntity
                .status(status.value())
                .body(body);
    }
}
