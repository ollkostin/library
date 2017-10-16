package ru.practice.kostin.library.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practice.kostin.library.dao.BookDao;
import ru.practice.kostin.library.dao.extractor.ListBookResultSetExtractor;
import ru.practice.kostin.library.model.Book;
import ru.practice.kostin.library.service.type.OrderType;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao {
    private String FETCH = "SELECT b.isn, b.name, b.author, u.id, u.username " +
            "FROM book b " +
            "LEFT JOIN user u ON u.id = b.user_id ";
    private String LIMIT_OFFSET = " LIMIT ? OFFSET ?";
    private String GET = "SELECT b.isn, b.name, b.author, b.user_id, u.id, u.username " +
            "FROM book b " +
            "LEFT JOIN user u ON u.id = b.user_id " +
            "WHERE b.isn = ?";
    private String INSERT = "INSERT INTO book (isn,name,author) VALUES (?,?,?)";
    private String UPDATE = "UPDATE book SET name = ?, author = ?, user_id = ? WHERE isn = ?";
    private String DELETE = "DELETE FROM book WHERE isn = ?";
    private String COUNT = "SELECT COUNT(*) FROM book b";
    private String BOOKS_BY_USER_ID = "SELECT b.isn, b.name, b.author, u.id, u.username " +
            "FROM book b " +
            "LEFT JOIN user u ON u.id = b.user_id " +
            "WHERE u.id = ?";
    private String ORDER_BY_AUTHOR = " ORDER BY b.author ";
    private String ORDER_BY_NAME = " ORDER BY b.name ";

    private JdbcTemplate jdbcTemplate;

    private ListBookResultSetExtractor listBookResultSetExtractor = new ListBookResultSetExtractor();

    @Override
    public int count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public List<Book> fetch() {
        return jdbcTemplate.query(FETCH, listBookResultSetExtractor);

    }

    @Override
    public List<Book> fetch(int offset, int limit, OrderType sort, boolean desc) {
        StringBuffer query = new StringBuffer();
        query.append(FETCH);
        if (sort.equals(OrderType.AUTHOR)) {
            query.append(ORDER_BY_AUTHOR);
        } else if (sort.equals(OrderType.NAME)) {
            query.append(ORDER_BY_NAME);
        }
        if (desc)
            query.append(" DESC ");
        query.append(LIMIT_OFFSET);
        return jdbcTemplate
                .query(query.toString(), new Object[]{limit, offset}, listBookResultSetExtractor);
    }

    @Override
    public Book get(String isn) {
        Book book;
        try {
            book = jdbcTemplate
                    .queryForObject(GET, new Object[]{isn}, new BeanPropertyRowMapper<>(Book.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return book;
    }

    @Override
    public String insert(Book book) {
        jdbcTemplate.update(INSERT, book.getIsn(), book.getName(), book.getAuthor());
        return book.getIsn();
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
