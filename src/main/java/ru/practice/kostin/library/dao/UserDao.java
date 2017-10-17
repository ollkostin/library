package ru.practice.kostin.library.dao;

import ru.practice.kostin.library.model.User;

import java.util.List;

public interface UserDao {

    List<User> fetch();

    User getById(Integer id);

    User getByUsername(String username);

    int insert(User user);

    int update(User user);

    int delete(Integer id);
}
