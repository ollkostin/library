package ru.practice.kostin.library.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
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


    public int deleteUser(Integer id) throws NotFoundException, AffectedRowsCountMismatchException {
        User user = userDao.getById(id);
        if (user == null) {
            throw new NotFoundException("user");
        }
        int affectedRowsCount = userDao.delete(id);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("delete error");
        }
        return affectedRowsCount;
    }

    public int editUser(UserDto userDto) throws IllegalArgumentException, UserAlreadyExistsException, NotFoundException, AffectedRowsCountMismatchException {
        UserDtoValidator.validateUserDto(userDto);
        User user = userDao.getById(userDto.getId());
        if (user == null) {
            throw new NotFoundException("user");
        }
        User userByUsername = userDao.getByUsername(userDto.getUsername());
        if (userByUsername != null && !user.getId().equals(userByUsername.getId())) {
            throw new UserAlreadyExistsException("exists");
        }
        user.setUsername(userDto.getUsername());
        user.setPasswordHash(bCryptPasswordEncoder.encode(userDto.getPassword()));
        int affectedRowsCount = userDao.update(user);
        if (affectedRowsCount != 1) {
            throw new AffectedRowsCountMismatchException("update error");
        }
        return affectedRowsCount;
    }


    public int createUser(UserDto userDto) throws IllegalArgumentException, UserAlreadyExistsException {
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
