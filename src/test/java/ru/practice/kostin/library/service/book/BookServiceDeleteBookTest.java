package ru.practice.kostin.library.service.book;

import javassist.NotFoundException;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceDeleteBookTest {
    private BookService bookService;
    @Mock
    private UserDao userDao;
    @Mock
    private BookDao bookDao;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        bookService = new BookService(bookDao, userDao);
    }

    @Test
    public void whenDeleteBookExpectSuccess() throws Exception {
        String isn = "978-5-17-045981-0";
        when(bookDao.get(isn)).thenReturn(new Book(isn, "", ""));
        when(bookDao.delete(isn)).thenReturn(1);
        assertEquals(1, bookService.deleteBook(isn));
    }

    @Test(expected = NotFoundException.class)
    public void whenDeleteBookShouldThrowNotFoundException() throws Exception {
        String isn = "978-5-271-17653-1";
        when(bookDao.get(isn)).thenReturn(null);
        bookService.deleteBook(isn);
    }

    @Test(expected = AffectedRowsCountMismatchException.class)
    public void whenDeleteBookShouldThrowAffectedRowsCountMismatchException() throws Exception {
        String isn = "978-5-271-17653-1";
        when(bookDao.get(isn)).thenReturn(new Book());
        when(bookDao.delete(isn)).thenReturn(0);
        bookService.deleteBook(isn);
    }

}
