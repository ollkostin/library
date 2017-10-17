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
import ru.practice.kostin.library.exception.NotAcceptableException;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.BookService;
import ru.practice.kostin.library.service.dto.BookDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTakeBookTest {
    private BookService bookService;
    @Mock
    private UserDao userDao;
    @Mock
    private BookDao bookDao;

    private BookDto bookDto;
    private Book book;
    private User user;
    private String isn;
    private String name;
    private String author;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookDao, userDao);
        isn = "978-5-17-045981-0";
        name = "name";
        author = "author";
        bookDto = new BookDto(isn, name, author);
        user = new User(1, "user1", "");
    }

    @After
    public void tearDown() throws Exception {
        bookDto = null;
        book = null;
        user = null;
    }

    @Test
    public void whenTakeBookExpectSuccess() throws Exception {
        book = new Book(isn, name, author);
        when(bookDao.get(isn)).thenReturn(book);
        when(userDao.getById(1)).thenReturn(user);
        when(bookDao.update(book)).thenReturn(1);
        assertEquals(1, bookService.takeBook(bookDto.getIsn(), user.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void whenTakeBookShouldThrowNotFoundExceptionWithBookReason() throws Exception {
        when(bookDao.get(isn)).thenReturn(null);
        bookService.takeBook(bookDto.getIsn(), user.getId());
    }

    @Test(expected = NotFoundException.class)
    public void whenTakeBookShouldThrowNotFoundExceptionWithUserReason() throws Exception {
        book = new Book(isn, name, author);
        when(bookDao.get(isn)).thenReturn(book);
        when(userDao.getById(any())).thenReturn(null);
        bookService.takeBook(bookDto.getIsn(), user.getId());
    }


    @Test(expected = NotAcceptableException.class)
    public void whenTakeBookShouldThrowNotAcceptableException() throws Exception {
        book = new Book(isn, name, author, new User(2,"user2",""));
        when(bookDao.get(isn)).thenReturn(book);
        bookService.takeBook(bookDto.getIsn(), user.getId());
    }

    @Test(expected = AffectedRowsCountMismatchException.class)
    public void whenTakeBookShouldThrowAffectedRowsCountMismatchException() throws Exception {
        book = new Book(isn, name, author);
        when(bookDao.get(isn)).thenReturn(book);
        when(userDao.getById(any())).thenReturn(user);
        when(bookDao.update(book)).thenReturn(0);
        assertNotEquals(1, bookService.takeBook(bookDto.getIsn(), user.getId()));
    }
}
