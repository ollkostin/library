package ru.practice.kostin.library.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.exception.NotAcceptableException;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.dto.BookDto;
import ru.practice.kostin.library.service.dto.PageDto;
import ru.practice.kostin.library.service.type.OrderType;
import ru.practice.kostin.library.util.BookDtoValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private BookDao bookDao;
    private UserDao userDao;

    public PageDto<BookDto> getBooks(int offset, int limit, String sort, boolean desc) {
        int totalCount = bookDao.count();
        List<Book> books = bookDao.fetch(offset * limit, limit, OrderType.valueOf(sort.toUpperCase()), desc);
        List<BookDto> bookDtos = books.stream()
                .map(this::buildBookDtoFromEntity)
                .collect(Collectors.toList());
        return buildPageDto(bookDtos, offset, limit, totalCount);
    }

    public void takeBook(String isn, Integer userId) throws NotFoundException {
        Book book = bookDao.get(isn);
        if (book == null) {
            throw new NotFoundException("book");
        }
        if (book.getUser() != null) {
            throw new NotAcceptableException("taken");
        }
        User user = userDao.getById(userId);
        if (user == null) {
            throw new NotFoundException("user");
        }
        book.setUser(user);
        bookDao.update(book);
    }

    public void returnBook(String isn, Integer userId) throws NotFoundException {
        Book book = bookDao.get(isn);
        if (book == null) {
            throw new NotFoundException("book");
        }
        if (book.getUser() == null) {
            throw new NotAcceptableException("returned");
        }
        User user = userDao.getById(userId);
        if (user == null) {
            throw new NotFoundException("user");
        }
        if (!book.getUser().getId().equals(user.getId())) {
            throw new NotAcceptableException("user mismatch");
        }
        book.setUser(null);
        bookDao.update(book);
    }

    public String createBook(BookDto bookDto) throws BookAlreadyExistsException, IllegalArgumentException {
        BookDtoValidator.validateBookDto(bookDto);
        Book book = bookDao.get(bookDto.getIsn());
        if (book != null) {
            throw new BookAlreadyExistsException("book");
        }
        book = buildBookEntityFromDto(bookDto);
        return bookDao.insert(book);
    }

    public void deleteBook(String isn) {
        bookDao.delete(isn);
    }

    public void editBook(BookDto bookDto) throws IllegalArgumentException {
        BookDtoValidator.validateBookDto(bookDto);
        bookDao.update(buildBookEntityFromDto(bookDto));
    }

    private BookDto buildBookDtoFromEntity(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setIsn(book.getIsn());
        bookDto.setName(book.getName());
        bookDto.setAuthor(book.getAuthor());
        User user = book.getUser();
        if (user != null) {
            bookDto.setUsername(user.getUsername());
        }
        return bookDto;
    }

    private Book buildBookEntityFromDto(BookDto bookDto) {
        Book book = new Book();
        book.setIsn(bookDto.getIsn());
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        if (bookDto.getUsername() != null && !bookDto.getUsername().isEmpty()) {
            User user = userDao.getByUsername(bookDto.getUsername());
            book.setUser(user);
        }
        return book;
    }

    private PageDto<BookDto> buildPageDto(List<BookDto> bookDtos, int offset, int limit,
                                          int totalCount) {
        PageDto<BookDto> pageDto = new PageDto<>();
        pageDto.setTotalCount(totalCount);
        pageDto.setData(bookDtos);
        pageDto.setOffset(offset);
        pageDto.setLimit(limit);
        return pageDto;
    }

    @Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
