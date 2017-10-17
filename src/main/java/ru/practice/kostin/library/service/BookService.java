package ru.practice.kostin.library.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
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


    public int takeBook(String isn, Integer userId)
            throws NotFoundException, NotAcceptableException, AffectedRowsCountMismatchException {
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
        int affectedRowsCount = bookDao.update(book);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("update error");
        }
        return affectedRowsCount;
    }


    public int returnBook(String isn, Integer userId) throws NotFoundException, NotAcceptableException, AffectedRowsCountMismatchException {
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
        int affectedRowsCount = bookDao.update(book);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("update error");
        }
        return affectedRowsCount;
    }


    public int createBook(BookDto bookDto) throws BookAlreadyExistsException, AffectedRowsCountMismatchException {
        BookDtoValidator.validateBookDto(bookDto);
        Book book = bookDao.get(bookDto.getIsn());
        if (book != null) {
            throw new BookAlreadyExistsException("book");
        }
        book = buildBookEntityFromDto(bookDto);
        int affectedRowsCount = bookDao.insert(book);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("insert error");
        }
        return affectedRowsCount;
    }


    public int deleteBook(String isn) throws NotFoundException, AffectedRowsCountMismatchException {
        Book book = bookDao.get(isn);
        if (book == null) {
            throw new NotFoundException("book");
        }
        int affectedRowsCount = bookDao.delete(isn);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("delete error");
        }
        return affectedRowsCount;
    }


    public int editBook(BookDto bookDto) throws NotFoundException, AffectedRowsCountMismatchException {
        BookDtoValidator.validateBookDto(bookDto);
        Book book = bookDao.get(bookDto.getIsn());
        if (book == null) {
            throw new NotFoundException("book");
        }
        book = buildBookEntityFromDto(bookDto);
        int affectedRowsCount = bookDao.update(book);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("update error");
        }
        return affectedRowsCount;
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
    public BookService(BookDao bookDao, UserDao userDao) {
        this.bookDao = bookDao;
        this.userDao = userDao;
    }
}
