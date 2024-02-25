package com.cs206.g2t2.exceptions.brawlStarsApi;

import org.springframework.http.HttpStatusCode;

public class ApiClientException extends RuntimeException {

    private int statusCode;

    public ApiClientException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        statusCode = httpStatusCode.value();
    }
}
