package ru.practice.kostin.library.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.practice.kostin.library.dao.UserDao;
import ru.practice.kostin.library.exception.AffectedRowsCountMismatchException;
import ru.practice.kostin.library.exception.UserAlreadyExistsException;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.service.UserService;
import ru.practice.kostin.library.service.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceEditUserTest {
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private User user1;
    private User user2;
    private UserDto userDto;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        userService = new UserService();
        userService.setUserDao(userDao);
        userService.setBCryptPasswordEncoder(passwordEncoder);
        userDto = new UserDto(1,"user2", "user2");
        user1 = new User(1,"user1", "");
        user2 = new User(2,"user2","");
    }


    @Test
    public void whenEditUserExpectSuccess() throws Exception {
        when(userDao.getById(userDto.getId())).thenReturn(user1);
        when(userDao.getByUsername(userDto.getUsername())).thenReturn(null);
        when(passwordEncoder.encode("user1"))
                .thenReturn("$2a$04$Fe0O/C22Vew7COTImUXsP.WOSpbAFmkJ7ylm9jyubiXI8.zMy.bOa");
        when(userDao.update(any())).thenReturn(1);
        assertEquals(1, userService.editUser(userDto));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void whenEditUserShouldThrowUserAlreadyExistsException() throws Exception {
        when(userDao.getById(userDto.getId())).thenReturn(user1);
        when(userDao.getByUsername(userDto.getUsername())).thenReturn(user2);
        userService.editUser(userDto);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenEditUserShouldThrowIllegalArgumentException() throws Exception {
        userDto = new UserDto("","");
        userService.editUser(userDto);
    }

    @Test(expected = AffectedRowsCountMismatchException.class)
    public void whenEditUserShouldThrowAffectedRowsCountMismatchException () throws Exception {
        when(userDao.getById(userDto.getId())).thenReturn(user1);
        when(userDao.getByUsername(userDto.getUsername())).thenReturn(null);
        when(passwordEncoder.encode("user1"))
                .thenReturn("$2a$04$Fe0O/C22Vew7COTImUXsP.WOSpbAFmkJ7ylm9jyubiXI8.zMy.bOa");
        when(userDao.update(any())).thenReturn(0);
        userService.editUser(userDto);
    }

}