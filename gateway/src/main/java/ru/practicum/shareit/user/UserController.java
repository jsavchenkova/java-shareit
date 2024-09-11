package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;

import java.util.List;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<Object>createUser(@Valid @RequestBody UserDtoCreate userDtoCreate){
        log.info("Creating user {}", userDtoCreate);
        return userClient.createUser(userDtoCreate);
    }

    @GetMapping
    public ResponseEntity<Object>getUsers() {
        log.info("Get users");
        return userClient.getUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id) {
        log.info("Get user by id {}", id);
        return userClient.getUserById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id, @Valid @RequestBody UserDtoUpdate userDtoUpdate){
        log.info("Update user {}", userDtoUpdate.getName());
        return userClient.updateUser(id, userDtoUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {
        return userClient.deleteUser(id);
    }
}
