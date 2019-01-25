package com.przemek.patronage.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handleIllegalArguments (IllegalArgumentException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<Object>(
                apiException, new HttpHeaders(), apiException.getStatus());
    }

    @ExceptionHandler({ RoomReservedException.class })
    public ResponseEntity<Object> handleRoomReserved (RoomReservedException ex) {
        ApiException apiException = new ApiException(
                HttpStatus.CONFLICT, ex.getMessage());
        return new ResponseEntity<Object>(
                apiException, new HttpHeaders(), apiException.getStatus());
    }
}