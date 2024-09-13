package ru.practicum.shareit.request;

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
import org.springframework.test.web.servlet.MvcResult;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@Slf4j
@WebMvcTest(ItemRequestController.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestControllerTest {

    @MockBean
    ItemRequestService service;
    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @Test
    @SneakyThrows
    void createItemRequest() {
        Long userId = (long)1;
        String description = "description";
        Long id = (long)123;
        ItemRequestCreateDto crdto = new ItemRequestCreateDto();
        crdto.setDescription(description);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(id);
        dto.setDescription(description);

        Mockito.when(service.createItemRequest(crdto, userId)).thenReturn(dto);

        mockMvc
                .perform(
                        post("/requests")
                                .content(mapper.writeValueAsString(crdto))
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(dto.getId()), Long.class))
                .andExpect(jsonPath("$.description", is(description), String.class));

        MvcResult mvcResult =
                mockMvc
                        .perform(
                                post("/requests")
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
    void getItemRequests() throws Exception {
        Long userId = (long)1;
        String description = "description";
        Long id = (long)123;

        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(id);
        dto.setDescription(description);

        Mockito.when(service.getItemRequests(userId)).thenReturn(List.of(dto));

        mockMvc
                .perform(
                        get("/requests")
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/requests")
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
    void getItemRequestById() throws Exception {
        Long userId = (long)1;
        String description = "description";
        Long id = (long)123;

        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(id);
        dto.setDescription(description);

        Mockito.when(service.getItemRequests(userId)).thenReturn(List.of(dto));

        mockMvc
                .perform(
                        get("/requests/" + id)
                                .header("X-Sharer-User-Id", userId)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


        MvcResult mvcResult =
                mockMvc
                        .perform(
                                get("/requests/" + id)
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