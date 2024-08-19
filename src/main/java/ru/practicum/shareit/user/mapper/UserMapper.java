package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static User mapUserDtoCreateToUser(UserDtoCreate userDtoCreate) {
        return User.builder()
                .name(userDtoCreate.getName())
                .email(userDtoCreate.getEmail())
                .build();
    }

    public static User mapUserDtoUpdateToUser(UserDtoUpdate userDtoUpdate) {
        return User.builder()
                .name(userDtoUpdate.getName())
                .email(userDtoUpdate.getEmail())
                .build();
    }

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
