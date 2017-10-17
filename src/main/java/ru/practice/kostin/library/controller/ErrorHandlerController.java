package ru.practice.kostin.library.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
import ru.practice.kostin.library.exception.NotAcceptableException;
import ru.practice.kostin.library.exception.UserAlreadyExistsException;
import ru.practice.kostin.library.service.dto.ErrorDto;

@ControllerAdvice
@RestController
public class ErrorHandlerController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(NotFoundException e) {
        return new ErrorDto("404", e.getMessage());
    }

    @ExceptionHandler({BookAlreadyExistsException.class, UserAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto alreadyExists(Exception e) {
        return new ErrorDto("409", e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgument(IllegalArgumentException e) {
        return new ErrorDto("400", e.getMessage());
    }

    @ExceptionHandler({Exception.class, AffectedRowsCountMismatchException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto internalServerError(Exception e) {
        return new ErrorDto("500", e.getMessage());
    }

    @ExceptionHandler(NotAcceptableException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorDto notAcceptableException(NotAcceptableException e) {
        return new ErrorDto("406", e.getMessage());
    }
}
