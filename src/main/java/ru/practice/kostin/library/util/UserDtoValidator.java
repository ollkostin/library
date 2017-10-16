package ru.practice.kostin.library.util;

import ru.practice.kostin.library.service.dto.UserDto;

public class UserDtoValidator {

    public static void validateUserDto(UserDto userDto) throws IllegalArgumentException {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("username");
        }
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("password");
        }
    }
}
