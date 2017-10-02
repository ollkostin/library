package ru.practice.kostin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;
import ru.practice.kostin.library.service.dto.PageDto;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity getBooks(@RequestParam(value = "offset") int offset,
                                   @RequestParam(value = "limit") int limit) {
        PageDto<BookDto> pageBookDto = bookService.getBooks(offset, limit);
        return ok(pageBookDto);
    }


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
