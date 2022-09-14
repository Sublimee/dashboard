package com.dashboard.controller;

import com.dashboard.converters.UserConverter;
import com.dashboard.domain.User;
import com.dashboard.dto.UserDto;
import com.dashboard.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    Iterable<UserDto> getUsers() {
        return StreamSupport.stream(userService.findAll().spliterator(), false)
                .map(UserConverter::toUserDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    UserDto getUser(@PathVariable("userId") Long userId) {
        return UserConverter.toUserDto(userService.findById(userId));
    }

    @PostMapping
    UserDto addUser(@RequestBody User user) {
        return UserConverter.toUserDto(userService.save(user));
    }
}
