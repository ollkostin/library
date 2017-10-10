package ru.practice.kostin.library.exception;

public class BookAlreadyExistsException extends Exception {

    public BookAlreadyExistsException(String message) {
        super(message);
    }

    public BookAlreadyExistsException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
