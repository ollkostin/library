package ru.practice.kostin.library.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
import ru.practice.kostin.library.security.UserDetailsImpl;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private BookService bookService;

    @GetMapping("/")
    public ResponseEntity getBooks(@RequestParam(value = "offset") int offset,
                                   @RequestParam(value = "limit") int limit,
                                   @RequestParam(value = "order", defaultValue = "author") String order,
                                   @RequestParam(value = "desc", defaultValue = "false") boolean desc) {
        return ok(bookService.getBooks(offset, limit, order, desc));
    }

    @PostMapping("/{isn}/take")
    public ResponseEntity takeBook(@PathVariable("isn") String isn) throws NotFoundException, AffectedRowsCountMismatchException {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.takeBook(isn, userDetails.getId());
        return ok().build();
    }

    @PostMapping("/{isn}/return")
    public ResponseEntity returnBook(@PathVariable("isn") String isn) throws NotFoundException, AffectedRowsCountMismatchException {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookService.returnBook(isn, userDetails.getId());
        return ok().build();
    }

    @DeleteMapping("/{isn}")
    public ResponseEntity deleteBook(@PathVariable("isn") String isn) throws NotFoundException, AffectedRowsCountMismatchException {
        bookService.deleteBook(isn);
        return ok().build();
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createBook(@RequestBody BookDto bookDto) throws BookAlreadyExistsException, IllegalArgumentException, AffectedRowsCountMismatchException {
        bookService.createBook(bookDto);
        UriComponents uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookDto.getIsn());
        return created(uri.toUri()).build();
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editBook(@RequestBody BookDto bookDto) throws IllegalArgumentException, NotFoundException, AffectedRowsCountMismatchException {
        bookService.editBook(bookDto);
        return ok().build();
    }

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }
}
