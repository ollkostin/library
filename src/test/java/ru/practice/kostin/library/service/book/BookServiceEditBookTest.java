package ru.practice.kostin.library.service.book;

import javassist.NotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceEditBookTest {
    private BookService bookService;
    @Mock
    private UserDao userDao;
    @Mock
    private BookDao bookDao;

    private BookDto bookDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookDao, userDao);
    }

    @After
    public void tearDown() throws Exception {
        bookDto = null;
    }

    @Test
    public void whenEditBookExpectSuccess() throws Exception {
        String isn = "978-5-17-045981-0";
        String name = "new name";
        String author = "new author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(bookDto.getIsn())).thenReturn(new Book(isn, "name", "author"));
        when(bookDao.update(any())).thenReturn(1);
        assertEquals(1, bookService.editBook(bookDto));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEditBookShouldThrowIllegalArgumentException() throws Exception {
        String isn = "978-5-17-045981-0";
        String name = "";
        String author = "";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.update(any())).thenReturn(1);
        bookService.editBook(bookDto);
    }

    @Test(expected = NotFoundException.class)
    public void whenEditBookShouldThrowNotFoundException() throws Exception {
        String isn = "978-5-271-17653-1";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(null);
        bookService.editBook(bookDto);
    }

    @Test(expected = AffectedRowsCountMismatchException.class)
    public void whenEditBookShouldThrowAffectedRowsCountMismatchException() throws Exception {
        String isn = "978-5-271-17653-1";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(new Book(isn, name, author));
        when(bookDao.update(any())).thenReturn(0);
        bookService.editBook(bookDto);
    }
}
