package com.cs206.g2t2.exceptions.brawlStarsApi;

import org.springframework.http.HttpStatusCode;

public class ApiServerException extends RuntimeException {

    private int statusCode;

    public ApiServerException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        statusCode = httpStatusCode.value();
    }
}
