package ru.practice.kostin.library.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.UserAlreadyExistsException;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.UserService;
import ru.practice.kostin.library.service.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceCreateUserTest {
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user;
    private UserDto userDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        userService.setUserDao(userDao);
        userService.setBCryptPasswordEncoder(passwordEncoder);
        userDto = new UserDto("user1", "user1");
        user = new User("user1", "");
    }

    @Test
    public void whenCreateUserExpectSuccess() throws Exception {
        when(userDao.getByUsername(user.getUsername())).thenReturn(null);
        when(passwordEncoder.encode("user1")).thenReturn("$2a$04$wdef30mrmL7L4N/jF9quN.7ad9dnAoAINCBulFRC.w3w6vRU96sBO");
        when(userDao.insert(any())).thenReturn(1);
        assertEquals(1, userService.createUser(userDto));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void whenCreateUserShouldThrowUserAlreadyExistsException() throws Exception {
        when(userDao.getByUsername(user.getUsername())).thenReturn(user);
        userService.createUser(userDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateUserShouldThrowIllegalArgumentException() throws Exception {
        userDto = new UserDto("","");
        userService.createUser(userDto);
    }

}