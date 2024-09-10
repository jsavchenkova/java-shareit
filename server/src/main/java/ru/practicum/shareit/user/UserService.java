package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    public UserDto createUser(UserDtoCreate userDtoCreate) {
        User user = UserMapper.mapUserDtoCreateToUser(userDtoCreate);
        return UserMapper.mapUserToUserDto(userJpaRepository.save(user));
    }

    public List<UserDto> getUsers() {
        return userJpaRepository.findAll().stream()
                .map(UserMapper::mapUserToUserDto)
                .toList();
    }

    public UserDto getUserById(long id) {
        Optional<User> user = userJpaRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return UserMapper.mapUserToUserDto(user.get());
    }

    public boolean isEmailExists(String email) {
        return (userJpaRepository.findByEmailContainingIgnoreCase(email).size() > 0);
    }

    public UserDto updateUser(long id, UserDtoUpdate userDto) {
        User user = UserMapper.mapUserDtoUpdateToUser(userDto);
        Optional<User> oUser = userJpaRepository.findById(id);
        if (oUser.isEmpty()) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        User oldUser = oUser.get();
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            oldUser.setName(user.getName());
        }
        return UserMapper.mapUserToUserDto(userJpaRepository.save(oldUser));
    }

    public void deleteUser(long id) {
        userJpaRepository.deleteById(id);
    }
}
