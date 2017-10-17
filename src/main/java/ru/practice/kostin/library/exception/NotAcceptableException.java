package ru.practice.kostin.library.exception;

public class NotAcceptableException extends RuntimeException {

    public NotAcceptableException(String message) {
        super(message);
    }

    public NotAcceptableException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
