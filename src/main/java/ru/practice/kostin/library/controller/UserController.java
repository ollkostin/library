package ru.practice.kostin.library.controller;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import ru.practice.kostin.library.exception.UserAlreadyExistsException;
import ru.practice.kostin.library.security.UserDetailsImpl;
import ru.practice.kostin.library.service.UserService;
import ru.practice.kostin.library.service.dto.UserDto;

import static org.springframework.http.ResponseEntity.created;
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

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserDto userDto) throws IllegalArgumentException, UserAlreadyExistsException {
        Integer userId = userService.createUser(userDto);
        UriComponents uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(userId);
        return created(uri.toUri()).build();
    }

    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity editUser(@RequestBody UserDto userDto) throws IllegalArgumentException, NotFoundException {
        userService.editUser(userDto);
        return ok().build();
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
