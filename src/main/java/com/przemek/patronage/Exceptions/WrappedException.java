package com.przemek.patronage.Exceptions;

public class WrappedException extends RuntimeException {
    public Throwable cause;

    public WrappedException(Throwable cause) {
        this.cause = cause;
    }

    public static WrappedException throwWrapped(Throwable t) {
        throw new WrappedException(t);
    }
}
