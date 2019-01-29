package com.przemek.patronage.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArguments(IllegalArgumentException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({RoomReservedException.class})
    public ResponseEntity<Object> handleRoomReserved(RoomReservedException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<>(
                apiException, new HttpHeaders(), apiException.getStatus());
    }
}