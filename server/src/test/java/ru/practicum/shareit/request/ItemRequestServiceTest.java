package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class ItemRequestServiceTest {

    private final ItemRequestJpaRepository repository;

    @Test
    void createItemRequest() {
        ItemRequest itemRequest = ItemRequest.builder()
                .created(LocalDateTime.now())
                .authorId(1L)
                .description("description")
                .build();

        itemRequest = repository.save(itemRequest);
        Assertions.assertNotNull(itemRequest.getId());
    }

    @Test
    void getItemRequests() {
        ItemRequest itemRequest = ItemRequest.builder()
                .created(LocalDateTime.now())
                .authorId(1L)
                .description("description")
                .build();

        itemRequest = repository.save(itemRequest);

        List<ItemRequest> list = repository.findAll();

        Assertions.assertEquals(1, list.size());
    }

    @Test
    void getItemRequestById() {
        ItemRequest itemRequest = ItemRequest.builder()
                .created(LocalDateTime.now())
                .authorId(1L)
                .description("description")
                .build();

        itemRequest = repository.save(itemRequest);

        Optional<ItemRequest> result = repository.findById(itemRequest.getId());

        Assertions.assertTrue(result.isPresent());
    }
}