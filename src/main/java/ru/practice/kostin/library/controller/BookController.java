package ru.practice.kostin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity getBooks() {
        List<BookDto> bookDtos = bookService.getBooks();
        return ok(bookDtos);
    }

    @GetMapping("/offset={offset}&limit={limit}")
    public ResponseEntity getBooks(@PathVariable("offset") int offset,
                                   @PathVariable("limit") int limit) {
        List<BookDto> bookDtos = bookService.getBooks(offset, limit);
        return ok(bookDtos);
    }


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
