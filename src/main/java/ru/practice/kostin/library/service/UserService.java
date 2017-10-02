package ru.practice.kostin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDao userDao;

    public List<UserDto> getUsers() {
        List<User> users = userDao.fetch();
        return users.stream()
                .map(this::buildUserDtoFromEntity)
                .collect(Collectors.toList());
    }

    public User getUserByUsername(String username) {
        return userDao.getByUsername(username);
    }

    private UserDto buildUserDtoFromEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }


    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
