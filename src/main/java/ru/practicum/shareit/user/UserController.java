package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.exception.UserAlreadyExisxtsException;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDtoCreate userDtoCreate) {
        if (userService.isEmailExists(userDtoCreate.getEmail())) {
            String errorMessage = "Пользователь с таким email уже существует";
            log.error(errorMessage);
            throw new UserAlreadyExisxtsException(errorMessage);
        }
        return userService.createUser(userDtoCreate);
    }

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }

    @PatchMapping("/{id}")
    public UserDto updateUser(@PathVariable long id, @Valid @RequestBody UserDtoUpdate userDtoUpdate) {
        if (userDtoUpdate.getEmail() != null && userService.isEmailExists(userDtoUpdate.getEmail())) {
            String errorMessage = "Пользователь с таким email уже существует";
            log.error(errorMessage);
            throw new UserAlreadyExisxtsException(errorMessage);
        }
        return userService.updateUser(id, userDtoUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
