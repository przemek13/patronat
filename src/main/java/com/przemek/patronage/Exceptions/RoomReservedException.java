package com.przemek.patronage.Exceptions;

public class RoomReservedException extends IllegalArgumentException {
    public RoomReservedException(String message) {
        super(message);
    }
}