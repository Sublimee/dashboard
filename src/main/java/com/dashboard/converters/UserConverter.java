package com.dashboard.converters;

import com.dashboard.domain.User;
import com.dashboard.dto.UserDto;

public class UserConverter {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .roleId(user.getRole().getId())
                .build();
    }
}