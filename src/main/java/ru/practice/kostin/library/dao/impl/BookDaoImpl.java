package ru.practice.kostin.library.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.extractor.ListBookResultSetExtractor;
import ru.practice.kostin.library.model.Book;

import java.sql.Types;
import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private String FETCH = "SELECT b.isn, b.name, b.author, u.id, u.username " +
            "FROM book b " +
            "LEFT JOIN user u ON u.id = b.user_id " +
            "ORDER BY b.name";
    private String FETCH_LIMIT_OFFSET = FETCH + " LIMIT ? OFFSET ?";
    private String GET = "SELECT b.isn, b.name, b.author, b.user_id, u.id, u.username " +
            "FROM book b " +
            "LEFT JOIN user u ON u.id = b.user_id " +
            "WHERE b.isn = ?";
    private String INSERT = "INSERT INTO book (isn,name,author) VALUES (?,?,?)";
    private String UPDATE = "UPDATE book SET name = ?, author = ?, user_id = ? WHERE isn = ?";
    private String DELETE = "DELETE FROM book WHERE isn = ?";

    private JdbcTemplate jdbcTemplate;

    private ListBookResultSetExtractor listBookResultSetExtractor = new ListBookResultSetExtractor();

    @Override
    public List<Book> fetch() {
        return jdbcTemplate.query(FETCH, listBookResultSetExtractor);

    }

    @Override
    public List<Book> fetch(int offset, int limit) {
        return jdbcTemplate
                .query(FETCH_LIMIT_OFFSET, new Object[]{limit, offset}, listBookResultSetExtractor);
    }

    @Override
    public Book get(String isn) {
        return jdbcTemplate
                .queryForObject(GET, new Object[]{isn}, new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public void insert(Book book) {
        jdbcTemplate.update(INSERT, book.getIsn(), book.getName(), book.getAuthor());
    }

    @Override
    public void update(Book book) {
        jdbcTemplate.update(UPDATE,
                new Object[]{book.getName(), book.getAuthor(), book.getUser() != null ? book.getUser().getId() : null, book.getIsn()});
    }

    @Override
    public void delete(String isn) {
        jdbcTemplate.update(DELETE, new Object[]{isn});
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}