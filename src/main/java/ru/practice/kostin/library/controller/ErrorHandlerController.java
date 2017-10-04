package ru.practice.kostin.library.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.service.dto.ErrorDto;

@ControllerAdvice
@RestController
public class ErrorHandlerController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(NotFoundException e) {
        return new ErrorDto(e.getMessage());
    }

    @ExceptionHandler(BookAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto bookAlreadyExists(BookAlreadyExistsException e) {
        return new ErrorDto(e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorDto illegalArgument(IllegalArgumentException e) {
        return new ErrorDto(e.getMessage());
    }
}
