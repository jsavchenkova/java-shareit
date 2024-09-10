package ru.practicum.shareit.user.mapper;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.model.User;

public class UserMapper {
    public static User mapUserDtoCreateToUser(UserDtoCreate userDtoCreate) {
        User user = new User();
        user.setName(userDtoCreate.getName());
        user.setEmail(userDtoCreate.getEmail());
        return user;
    }

    public static User mapUserDtoUpdateToUser(UserDtoUpdate userDtoUpdate) {
        User user = new User();
        user.setName(userDtoUpdate.getName());
        user.setEmail(userDtoUpdate.getEmail());
        return user;
    }

    public static UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
