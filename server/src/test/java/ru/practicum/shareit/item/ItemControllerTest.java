package ru.practicum.shareit.item;

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
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(ItemController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemControllerTest {

    @MockBean
    private final ItemService service;
    @MockBean
    private final UserService userService;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createItem() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoCreate itemDtoCreate = ItemDtoCreate.builder()
                .name(name)
                .available(available)
                .description(description)
                .build();

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        UserDto userDto = UserDto.builder()
                .id(userId)
                .name("name")
                .email("email@email.el")
                .build();

        Mockito.when(service.createItem(itemDtoCreate, userId)).thenReturn(itemDto);
        Mockito.when(userService.getUserById(userId)).thenReturn(userDto);

        mockMvc
                .perform(
                        post("/items")
                                .content(mapper.writeValueAsString(itemDtoCreate))
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(name), String.class))
                .andExpect(jsonPath("$.available", is(available), Boolean.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/items")
                                        .content(mapper.writeValueAsString(itemDtoCreate))
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
    void updateItem() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDtoUpdate itemDtoUpdate = ItemDtoUpdate.builder()
                .name(name)
                .available(available)
                .description(description)
                .build();

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Mockito.when(service.updateItem(id, itemDtoUpdate)).thenReturn(itemDto);

        mockMvc
                .perform(
                        patch("/items/" + id)
                                .content(mapper.writeValueAsString(itemDtoUpdate))
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(name), String.class))
                .andExpect(jsonPath("$.available", is(available), Boolean.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                patch("/items/" + id)
                                        .content(mapper.writeValueAsString(itemDtoUpdate))
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
    void getItems() {
        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Mockito.when(service.getItemById(userId)).thenReturn(itemDto);

        mockMvc
                .perform(
                        get("/items")
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/items")
                                        .header("X-Sharer-User-Id", userId)
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }

    @SneakyThrows
    @Test
    void getItemById() {

        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Mockito.when(service.getItemById(id)).thenReturn(itemDto);

        mockMvc
                .perform(
                        get("/items/" + id)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/items/" + id)
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
    void searchItem() {

        long id = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        String text = "ame";

        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(id)
                .userId(userId)
                .description(description)
                .build();

        Mockito.when(service.searchItem(text)).thenReturn(List.of(itemDto));

        mockMvc
                .perform(
                        get("/items/search?text=" + text)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/items/search?text=" + text)
                                        .header("X-Sharer-User-Id", userId)
                                        .characterEncoding(StandardCharsets.UTF_8)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        log.info("Тело ответа: {}", responseBody);
    }


}