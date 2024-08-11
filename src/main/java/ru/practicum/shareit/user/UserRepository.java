package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserRepository {

    List<User> getUsers();

    User getUserById(long id);

    User createUser(User user);

    void deleteUserById(long id);

    boolean isEmailExists(String email);

    User updateUser(long id, User user);
}

