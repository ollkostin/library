package ru.practice.kostin.library.dao.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListBookResultSetExtractor implements ResultSetExtractor<List<Book>> {

    @Override
    public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Book> map = new HashMap<>();
        Book book;
        while (rs.next()) {
            String isn = rs.getString("isn");
            book = map.get(isn);
            if (book == null) {
                String name = rs.getString("name");
                String author = rs.getString("author");
                book = new Book(isn, name, author);
                map.put(isn, book);
            }
            User user = new User(rs.getInt("id"), rs.getString("username"));
            book.setUser(user);
        }
        return new ArrayList<>(map.values());
    }

}
