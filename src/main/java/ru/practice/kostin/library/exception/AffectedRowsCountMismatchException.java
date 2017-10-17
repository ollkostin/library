package ru.practice.kostin.library.exception;

public class AffectedRowsCountMismatchException extends Exception {

    public AffectedRowsCountMismatchException(String message) {
        super(message);
    }

    public AffectedRowsCountMismatchException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
