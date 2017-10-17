package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.service.type.OrderType;

import java.util.List;

public interface BookDao {

    List<Book> fetch();

    int count();

    List<Book> fetch(int offset, int limit, OrderType sort, boolean desc);

    Book get(String isn);

    int insert(Book book);

    int update(Book book);

    int delete(String isn);
}
