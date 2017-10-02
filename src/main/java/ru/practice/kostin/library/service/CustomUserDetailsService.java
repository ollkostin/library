package ru.practice.kostin.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.practice.kostin.library.model.User;
import ru.practice.kostin.library.model.UserDetailsImpl;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не был найден");
        }
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPasswordHash());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
