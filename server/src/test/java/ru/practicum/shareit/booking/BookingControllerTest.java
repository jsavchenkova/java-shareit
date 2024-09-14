package ru.practicum.shareit.booking;

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
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(BookingController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingControllerTest {

    @MockBean
    private final BookingService service;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createBookingDto() {
        long itemid = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean available = true;
        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(available)
                .id(itemid)
                .userId(userId)
                .description(description)
                .build();


        String userName = "name";
        String email = "email@email.dk";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .build();


        long id = 123;

        BookingCreateDto crdto = new BookingCreateDto();
        crdto.setItemId(itemid);
        BookingDto dto = BookingDto.builder()
                .id(id)
                .item(itemDto)
                .booker(userDto)
                .build();


        Mockito.when(service.createBooking(crdto, userId)).thenReturn(dto);

        mockMvc
                .perform(
                        post("/bookings")
                                .content(mapper.writeValueAsString(crdto))
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/bookings")
                                        .content(mapper.writeValueAsString(crdto))
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
    void updateBooking() {
        long itemid = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean approved = true;
        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(approved)
                .id(itemid)
                .userId(userId)
                .description(description)
                .build();


        String userName = "name";
        String email = "email@email.dk";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .build();

        long id = 4;

        BookingDto dto = BookingDto.builder()
                .id(id)
                .item(itemDto)
                .booker(userDto)
                .build();

        Mockito.when(service.updateBooking(id, userId, approved)).thenReturn(dto);

        mockMvc
                .perform(
                        patch("/bookings/" + id + "?approved=" + approved)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                patch("/bookings/" + id + "?approved=" + approved)
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
    void findAll() {
        long itemid = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean approved = true;
        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(approved)
                .id(itemid)
                .userId(userId)
                .description(description)
                .build();


        String userName = "name";
        String email = "email@email.dk";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .build();

        long id = 4;

        BookingDto dto = BookingDto.builder()
                .id(id)
                .item(itemDto)
                .booker(userDto)
                .build();

        Mockito.when(service.findAll()).thenReturn(List.of(dto));

        mockMvc
                .perform(
                        get("/bookings")
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/bookings")
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
    void findByOwner() {
        long itemid = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean approved = true;
        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(approved)
                .id(itemid)
                .userId(userId)
                .description(description)
                .build();


        String userName = "name";
        String email = "email@email.dk";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .build();

        long id = 4;

        BookingDto dto = BookingDto.builder()
                .id(id)
                .item(itemDto)
                .booker(userDto)
                .build();


        Mockito.when(service.findByOwner(userId)).thenReturn(List.of(dto));

        mockMvc
                .perform(
                        get("/bookings/owner")
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/bookings/owner")
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
    void findById() {
        long itemid = 1;
        long userId = 3;
        String name = "name";
        String description = "description";
        boolean approved = true;
        ItemDto itemDto = ItemDto.builder()
                .name(name)
                .available(approved)
                .id(itemid)
                .userId(userId)
                .description(description)
                .build();


        String userName = "name";
        String email = "email@email.dk";
        UserDto userDto = UserDto.builder()
                .id(userId)
                .name(userName)
                .email(email)
                .build();

        long id = 4;

        BookingDto dto = BookingDto.builder()
                .id(id)
                .item(itemDto)
                .booker(userDto)
                .build();

        Mockito.when(service.findById(id, userId)).thenReturn(dto);

        mockMvc
                .perform(
                        get("/bookings/" + id)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/bookings/" + id)
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