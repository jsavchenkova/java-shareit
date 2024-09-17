package ru.practicum.shareit.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserDtoCreate;
import ru.practicum.shareit.user.dto.UserDtoUpdate;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(UserController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserControllerTest {

    @MockBean
    private final UserService service;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createUser() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";
        UserDtoCreate userDtoCreate = UserDtoCreate.builder()
                .name(name)
                .email(email)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(service.createUser(userDtoCreate)).thenReturn(userDto);

        mockMvc
                .perform(
                        post("/users")
                                .content(mapper.writeValueAsString(userDtoCreate))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(name), String.class))
                .andExpect(jsonPath("$.email", is(email), String.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/users")
                                        .content(mapper.writeValueAsString(userDtoCreate))
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void getUsers() {
        long id = 1;
        String name = "name";
        String email = "email@email.dk";
        UserDtoCreate userDtoCreate = UserDtoCreate.builder()
                .name(name)
                .email(email)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(service.getUsers()).thenReturn(List.of(userDto));

        mockMvc
                .perform(
                        get("/users")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/users")
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void getUserById() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";
        UserDtoCreate userDtoCreate = UserDtoCreate.builder()
                .name(name)
                .email(email)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();

        Mockito.when(service.getUserById(id)).thenReturn(userDto);

        mockMvc
                .perform(
                        get("/users/" + id)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(name), String.class))
                .andExpect(jsonPath("$.email", is(email), String.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/users/" + id)
                                        .header("X-Sharer-User-Id", userId)
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void updateUser() {
        long id = 1;
        String email = "email@email.dk";
        String newName = "newName";
        UserDtoUpdate userDtoUpdate = UserDtoUpdate.builder()
                .name(newName)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(newName)
                .email(email)
                .build();

        Mockito.when(service.updateUser(id, userDtoUpdate)).thenReturn(userDto);

        mockMvc
                .perform(
                        patch("/users/" + id)
                                .content(mapper.writeValueAsString(userDtoUpdate))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(newName), String.class))
                .andExpect(jsonPath("$.email", is(email), String.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                patch("/users/" + id)
                                        .content(mapper.writeValueAsString(userDtoUpdate))
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void deleteUser() {
        long id = 1;
        Mockito.doNothing().when(service).deleteUser(id);

        mockMvc
                .perform(
                        delete("/users/" + id)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                delete("/users/" + id)
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void createUserUserAlreadyExisxtsException() {
        long id = 1;
        long userId = 2;
        String name = "name";
        String email = "email@email.dk";
        UserDtoCreate userDtoCreate = UserDtoCreate.builder()
                .name(name)
                .email(email)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .build();


        Mockito.when(service.isEmailExists(any())).thenReturn(true);

        mockMvc
                .perform(
                        post("/users")
                                .content(mapper.writeValueAsString(userDtoCreate))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/users")
                                        .content(mapper.writeValueAsString(userDtoCreate))
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @Test
    @SneakyThrows
    void updateUserUserAlreadyExisxtsException() {
        long id = 1;
        String email = "email@email.dk";
        String newName = "newName";
        UserDtoUpdate userDtoUpdate = UserDtoUpdate.builder()
                .name(newName)
                .email(email)
                .build();

        UserDto userDto = UserDto.builder()
                .id(id)
                .name(newName)
                .email(email)
                .build();

        Mockito.when(service.isEmailExists(any())).thenReturn(true);


        mockMvc
                .perform(
                        patch("/users/" + id)
                                .content(mapper.writeValueAsString(userDtoUpdate))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                patch("/users/" + id)
                                        .content(mapper.writeValueAsString(userDtoUpdate))
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isConflict())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }
}