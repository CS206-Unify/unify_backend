package com.cs206.g2t2.config;

import com.cs206.g2t2.exceptions.brawlStarsApi.ApiClientException;
import com.cs206.g2t2.exceptions.brawlStarsApi.ApiServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().is5xxServerError() ||
                httpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        //If no error return from function
        if (!hasError(httpResponse)) {
            return;
        }

        //Throws appropriate exceptions as per errors
        if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
            throw new ApiServerException(httpResponse.getStatusCode(), "Client provided incorrect parameters for the request.");

        } else if (httpResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            throw new ApiServerException(httpResponse.getStatusCode(), "Access denied, either because of missing/incorrect credentials or used API token does not grant access to the requested resource.");

        } else if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new ApiClientException(httpResponse.getStatusCode(), "Resource was not found, invalid Player Tag");

        } else if (httpResponse.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
            throw new ApiServerException(httpResponse.getStatusCode(), "Request was throttled, because amount of requests was above the threshold defined for the used API token.");

        } else if (httpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
            throw new ApiServerException(httpResponse.getStatusCode(), "Unknown error happened when handling the request.");

        } else if (httpResponse.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            throw new ApiServerException(httpResponse.getStatusCode(), "Service is temprorarily unavailable because of maintenance.");

        } else {
            throw new ApiServerException(httpResponse.getStatusCode(), "Unknown error");

        }
    }
}
