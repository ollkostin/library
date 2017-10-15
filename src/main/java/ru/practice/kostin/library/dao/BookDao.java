package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> fetch();

    int count();

    List<Book> fetch(int offset,int limit);

    Book get(String isn);

    String insert(Book book);

    void update(Book book);

    void delete(String isn);
}
