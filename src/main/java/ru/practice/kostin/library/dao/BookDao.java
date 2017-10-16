package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.service.type.OrderType;

import java.util.List;

public interface BookDao {

    List<Book> fetch();

    int count();

    List<Book> fetch(int offset, int limit, OrderType sort, boolean desc);

    Book get(String isn);

    void insert(Book book);

    void update(Book book);

    void delete(String isn);
}
