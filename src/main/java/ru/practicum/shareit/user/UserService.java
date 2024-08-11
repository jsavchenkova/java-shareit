package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryMemory userRepository;

    public UserDto createUser(UserDtoCreate userDtoCreate) {
        User user = UserMapper.mapUserDtoCreateToUser(userDtoCreate);
        return UserMapper.mapUserToUserDto(userRepository.createUser(user));
    }

    public List<UserDto> getUsers() {
        return userRepository.getUsers().stream()
                .map(UserMapper::mapUserToUserDto)
                .toList();
    }

    public UserDto getUserById(long id) {
        return UserMapper.mapUserToUserDto(userRepository.getUserById(id));
    }

    public boolean isEmailExists(String email) {
        return userRepository.isEmailExists(email);
    }

    public UserDto updateUser(long id, UserDtoUpdate userDto) {
        User user = UserMapper.mapUserDtoUpdateToUser(userDto);
        return UserMapper.mapUserToUserDto(userRepository.updateUser(id, user));
    }

    public void deleteUser(long id) {
        userRepository.deleteUserById(id);
    }
}
