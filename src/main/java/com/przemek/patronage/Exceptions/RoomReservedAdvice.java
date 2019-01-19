package com.przemek.patronage.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoomReservedAdvice {
    @ResponseBody
    @ExceptionHandler(RoomReservedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String sameNameHandler(RoomReservedException ex) {
        return ex.getMessage();
    }
}
