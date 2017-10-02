package ru.practice.kostin.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practice.kostin.library.model.UserDetailsImpl;
import ru.practice.kostin.library.service.UserService;
import ru.practice.kostin.library.service.dto.UserDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public ResponseEntity getUsers() {
        List<UserDto> userList = userService.getUsers();
        return ok(userList);
    }

    @GetMapping("/currentUsername")
    public ResponseEntity getCurrentUsername() {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ok(userDetails.getUsername());
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
