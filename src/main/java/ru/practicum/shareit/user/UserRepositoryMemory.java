package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepositoryMemory implements UserRepository {

    private HashMap<Long, User> users = new HashMap<>();
    private Long id = 0L;

    @Override
    public User createUser(User user) {
        user.setId(id);
        users.put(id, user);
        id++;
        return user;
    }

    public List<User> getUsers() {
        return null;
    }

    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new UserNotFoundException("Пользователь не найден");
        }
        return users.get(id);
    }

    @Override
    public void deleteUserById(long id) {
        users.remove(id);
    }

    @Override
    public boolean isEmailExists(String email) {
        long count = users.values().stream()
                .map(x -> x.getEmail())
                .filter(x -> x.equals(email))
                .count();
        return count > 0;
    }

    @Override
    public User updateUser(long id, User user) {
        User oldUser = getUserById(id);
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            oldUser.setEmail(user.getEmail());
        }
        if (user.getName() != null && !user.getName().isBlank()) {
            oldUser.setName(user.getName());
        }
        users.put(id, oldUser);
        return oldUser;
    }
}
