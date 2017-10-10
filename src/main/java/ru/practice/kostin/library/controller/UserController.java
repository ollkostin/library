package ru.practice.kostin.library.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.practice.kostin.library.model.UserDetailsImpl;
import ru.practice.kostin.library.service.UserService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @GetMapping("/")
    public ResponseEntity getUsers() {
        return ok(userService.getUsers());
    }

    @GetMapping("/currentUsername")
    public ResponseEntity getCurrentUsername() {
        UserDetailsImpl userDetails = (UserDetailsImpl)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ok(userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) throws NotFoundException {
        userService.deleteUser(id);
        return ok().build();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
