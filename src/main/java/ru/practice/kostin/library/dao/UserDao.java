package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.User;

import java.util.List;

public interface UserDao {

    List<User> fetch();

    User getById(Integer id);

    User getByUsername(String username);

    int insert(User user);

    void update(User user);

    void delete(Integer id);
}
