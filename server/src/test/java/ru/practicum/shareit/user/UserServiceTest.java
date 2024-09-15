package ru.practicum.shareit.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserJpaRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void createUser() {

        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";
        UserDtoCreate userDtoCreate = UserDtoCreate.builder()
                .name(name)
                .email(email)
                .build();

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(repository.save(any())).thenReturn(user);

        UserDto result = service.createUser(userDtoCreate);

        Assertions.assertEquals(result, userDto);
    }

    @Test
    void getUsers() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(repository.findAll()).thenReturn(List.of(user));

        List<UserDto> result = service.getUsers();

        Assertions.assertEquals(userDto, result.stream().findFirst().get());
    }

    @Test
    void getUserByIdUserNotFoundException() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.getUserById(1L));
    }

    @Test
    void getUserByIdr() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(repository.findById(any())).thenReturn(Optional.of(user));

        UserDto result = service.getUserById(id);

        Assertions.assertEquals(userDto, result);

    }

    @Test
    void isEmailExists() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        Mockito.when(repository.findByEmailContainingIgnoreCase(any())).thenReturn(List.of(user));

        boolean result = service.isEmailExists("text");

        Assertions.assertTrue(result);
    }

    @Test
    void updateUserUserNotFoundException() {
        long id = 1;
        String email = "email@email.dk";
        String newName = "newName";
        UserDtoUpdate userDtoUpdate = UserDtoUpdate.builder()
                .name(newName)
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> service.updateUser(id,userDtoUpdate));
    }

    @Test
    void updateUserUser() {
        long id = 1;
        String email = "email@email.dk";
        String newName = "newName";
        UserDtoUpdate userDtoUpdate = UserDtoUpdate.builder()
                .name(newName)
                .build();

        User user = new User();
        user.setId(id);
        user.setName(newName);
        user.setEmail(email);

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(newName)
                .email(email)
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(repository.save(any())).thenReturn(user);

        UserDto result = service.updateUser(id, userDtoUpdate);
        Assertions.assertEquals(userDto, result);
    }

    @Test
    void updateUserUserNull() {
        long id = 1;
        String email = "email@email.dk";
        String newName = "newName";
        UserDtoUpdate userDtoUpdate = UserDtoUpdate.builder()
                .build();

        User user = new User();
        user.setId(id);


        UserDto userDto = UserDto.builder()
                .id(id)
                .build();

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(repository.save(any())).thenReturn(user);

        UserDto result = service.updateUser(id, userDtoUpdate);
        Assertions.assertEquals(userDto, result);
    }
}