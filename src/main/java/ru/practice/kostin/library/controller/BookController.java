package ru.practice.kostin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.practice.kostin.library.model.UserDetailsImpl;
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

    @PostMapping("/{isn}/take")
    public ResponseEntity takeBook(@PathVariable("isn") String isn) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.takeBook(isn, userDetails.getId());
        return ok().build();
    }

    @PostMapping("/{isn}/return")
    public ResponseEntity returnBook(@PathVariable("isn") String isn) {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.returnBook(isn, userDetails.getId());
        return ok().build();
    }

    @DeleteMapping("/{isn}")
    public ResponseEntity deleteBook(@PathVariable("isn") String isn) {
        bookService.deleteBook(isn);
        return ok().build();
    }


    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
