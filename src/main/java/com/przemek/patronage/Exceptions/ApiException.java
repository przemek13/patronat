package com.przemek.patronage.Exceptions;

import org.springframework.http.HttpStatus;

public class ApiException {
    private final HttpStatus status;
    private final String message;

    protected ApiException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
