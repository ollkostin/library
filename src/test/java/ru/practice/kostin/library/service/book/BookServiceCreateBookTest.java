package ru.practice.kostin.library.service.book;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
import ru.practice.kostin.library.exception.BookAlreadyExistsException;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookServiceCreateBookTest {
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
    public void whenCreateBooksExpectSuccess() throws Exception {
        String isn = "978-5-17-045981-0";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(null);
        when(bookDao.insert(any())).thenReturn(1);
        assertEquals(1, bookService.createBook(bookDto));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateBookShouldThrowIllegalArgumentException() throws Exception {
        String isn = "";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(null);
        when(bookDao.insert(any())).thenReturn(1);
        assertEquals(1, bookService.createBook(bookDto));
    }

    @Test(expected = BookAlreadyExistsException.class)
    public void whenCreateBookShouldThrowBookAlreadyExistsException() throws Exception {
        String isn = "978-5-17-045981-0";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(new Book(isn, name, author));
        bookService.createBook(bookDto);
    }

    @Test(expected = AffectedRowsCountMismatchException.class)
    public void whenCreateBookShouldThrowAffectedRowsCountMismatchException() throws Exception {
        String isn = "978-5-17-045981-0";
        String name = "name";
        String author = "author";
        bookDto = new BookDto(isn, name, author);
        when(bookDao.get(isn)).thenReturn(null);
        when(bookDao.insert(any())).thenReturn(0);
        bookService.createBook(bookDto);
    }
}
