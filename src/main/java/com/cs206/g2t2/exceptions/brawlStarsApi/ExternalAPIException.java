package com.cs206.g2t2.exceptions.brawlStarsApi;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class ExternalAPIException extends RuntimeException {

    private int statusCode;

    public ExternalAPIException(HttpStatusCode httpStatusCode, String message) {
        super(message);
        statusCode = httpStatusCode.value();
    }
}
