package ru.practice.kostin.library.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        String isn = resultSet.getString("isn");
        String name = resultSet.getString("name");
        String author = resultSet.getString("author");
        Integer userId = resultSet.getInt("id");
        String username = resultSet.getString("username");
        User user = null;
        if (userId != 0) {
            user = new User(userId, username);
        }
        return new Book(isn, name, author, user);
    }
}
