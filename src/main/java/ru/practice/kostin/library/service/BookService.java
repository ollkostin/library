package ru.practice.kostin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public List<BookDto> getBooks() {
        List<Book> books = bookDao.fetch();
        return books.stream().map(this::buildBookDtoFromEntity)
                .collect(Collectors.toList());
    }

    public List<BookDto> getBooks(int offset, int limit) {
        List<Book> books = bookDao.fetch(offset, limit);
        return books.stream().map(this::buildBookDtoFromEntity)
                .collect(Collectors.toList());
    }

    private BookDto buildBookDtoFromEntity(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setIsn(book.getIsn());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor());
        Optional<User> user = Optional.ofNullable(book.getUser());
        if (user.isPresent()) {
            bookDto.setUsername(user.get().getUsername());
        }
        return bookDto;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
}
