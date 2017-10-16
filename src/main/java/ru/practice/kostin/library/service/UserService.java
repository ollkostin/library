package ru.practice.kostin.library.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.UserAlreadyExistsException;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.dto.UserDto;
import ru.practice.kostin.library.util.UserDtoValidator;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    public void deleteUser(Integer id) throws NotFoundException {
        User user = userDao.getById(id);
        if (user == null) {
            throw new NotFoundException("user");
        }
        userDao.delete(id);
    }

    public void editUser(UserDto userDto) throws IllegalArgumentException, NotFoundException {
        UserDtoValidator.validateUserDto(userDto);
        User user = getUserByUsername(userDto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException("exists");
        }
        user = userDao.getById(userDto.getId());
        if (user == null) {
            throw new NotFoundException("user");
        }
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDao.update(user);
    }


    public Integer createUser(UserDto userDto) throws IllegalArgumentException, UserAlreadyExistsException {
        UserDtoValidator.validateUserDto(userDto);
        User user = userDao.getByUsername(userDto.getUsername());
        if (user != null) {
            throw new UserAlreadyExistsException("exists");
        }
        return userDao.insert(buildUserEntityFromDto(userDto));
    }

    private User buildUserEntityFromDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(userDto.getPassword()));
        return user;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
