package ru.practice.kostin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.dto.BookDto;
import ru.practice.kostin.library.service.dto.PageDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookDao bookDao;

    public PageDto<BookDto> getBooks(int offset, int limit) {
        int totalCount = bookDao.count();
        List<Book> books = bookDao.fetch(offset * limit, limit);
        List<BookDto> bookDtos = books.stream()
                .map(this::buildBookDtoFromEntity)
                .collect(Collectors.toList());
        PageDto<BookDto> pageDto = buildPageDto(bookDtos, offset, limit, totalCount);
        return pageDto;
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
}
