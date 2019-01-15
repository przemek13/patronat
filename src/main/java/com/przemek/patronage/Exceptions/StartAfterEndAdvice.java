package com.przemek.patronage.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class StartAfterEndAdvice {
    @ResponseBody
    @ExceptionHandler(StartAfterEndException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongDateHandler(StartAfterEndException ex) {
        return ex.getMessage();
    }
}
