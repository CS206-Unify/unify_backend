package com.cs206.g2t2.controller;

import com.cs206.g2t2.exceptions.badRequest.BadRequestException;
import com.cs206.g2t2.exceptions.brawlStarsApi.ExternalAPIException;
import com.cs206.g2t2.exceptions.forbidden.ForbiddenException;
import com.cs206.g2t2.exceptions.notFound.NotFoundException;
import com.cs206.g2t2.exceptions.unauthorized.UnauthorizedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
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
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException badRequestException) {
        Map<String, Object> body = returnMapFromException(badRequestException, HttpStatus.BAD_REQUEST);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<Object> handleUnauthorizedExceptions(UnauthorizedException unauthorizedException) {
        Map<String, Object> body = returnMapFromException(unauthorizedException, HttpStatus.UNAUTHORIZED);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<Object> handleForbiddenException(ForbiddenException forbiddenException) {
        Map<String, Object> body = returnMapFromException(forbiddenException, HttpStatus.FORBIDDEN);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(body);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        Map<String, Object> body = returnMapFromException(notFoundException, HttpStatus.NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(IllegalStateException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalStateException illegalStateException) {
        Map<String, Object> body = returnMapFromException(illegalStateException, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<Object> handleNullPointerException(NullPointerException nullPointerException) {
        Map<String, Object> body = returnMapFromException(nullPointerException, HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
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
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        //Find all the errors in the name and put into a list
        List<String> errors = new ArrayList<String>();
        List<ObjectError> objectErrorList = ex.getBindingResult().getAllErrors();
        for (ObjectError error : objectErrorList) {
            errors.add(error.getDefaultMessage());
        }

        //Create body of ResponseEntity
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("error", ex.getClass());
        body.put("message", errors.toString());
        return ResponseEntity
                .status(status.value())
                .body(body);
    }
}
