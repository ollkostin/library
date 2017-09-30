package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.Book;

import java.util.List;

public interface BookDao {

    List<Book> fetch();

    List<Book> fetch(int offset,int limit);

    Book get(String isn);

    void insert(Book book);

    void update(Book book);

    void delete(String isn);
}