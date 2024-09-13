package ru.practicum.shareit.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserServiceTest {

    private final UserJpaRepository repository;

    @Test
    void createUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");

        User savedUser = repository.save(user);
        Assertions.assertNotNull(user.getId());
    }

    @Test
    void getUsers() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        repository.save(user);
        User user1 = new User();
        user.setName("name1");
        user.setEmail("email1");
        repository.save(user1);

        List<User> result = repository.findAll();
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void getUserById() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        repository.save(user);
        User user1 = new User();
        user.setName("name1");
        user.setEmail("email1");
        user1 = repository.save(user1);

        Optional<User> result = repository.findById(user1.getId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user1.getId(), result.get().getId());
    }

    @Test
    void isEmailExists() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        repository.save(user);

        List<User> result = repository.findByEmailContainingIgnoreCase("email");

        Assertions.assertEquals(1, result.size());
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        User sv = repository.save(user);

        sv.setName("newName");
        User upd = repository.save(sv);

        Assertions.assertEquals("newName", upd.getName());

    }

    @Test
    void deleteUser() {
        User user = new User();
        user.setName("name");
        user.setEmail("email");
        User sv = repository.save(user);

        repository.deleteById(sv.getId());

        Optional<User> result = repository.findById(sv.getId());

        Assertions.assertTrue(result.isEmpty());
    }
}