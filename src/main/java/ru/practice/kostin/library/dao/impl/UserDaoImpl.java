package ru.practice.kostin.library.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.model.User;

import java.sql.Types;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    private String FETCH = "SELECT u.id, u.username, u.password_hash FROM user u ORDER BY u.username";
    private String GET_BY_ID = "SELECT u.id, u.username, u.password_hash FROM user u WHERE u.id = ?";
    private String GET_BY_USERNAME = "SELECT u.id, u.username, u.password_hash FROM user u WHERE u.username = ?";
    private String INSERT = "INSERT INTO user (username,password_hash) VALUES (?,?)";
    private String UPDATE = "UPDATE user SET username = ?, password_hash = ? WHERE id = ?";
    private String DELETE = "DELETE FROM user WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> fetch() {
        return jdbcTemplate.query(FETCH, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getById(Integer id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User getByUsername(String username) {
        return jdbcTemplate.queryForObject(GET_BY_USERNAME, new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public void insert(User user) {
        jdbcTemplate.update(INSERT, new Object[]{user.getUsername(), user.getPasswordHash()},
                new Object[]{Types.VARCHAR, Types.VARCHAR});
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(UPDATE, user.getUsername(), user.getPasswordHash(), user.getId());
    }

    @Override
    public void delete(Integer id) {
        jdbcTemplate.update(DELETE, new Object[]{id});
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
