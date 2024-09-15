package ru.practicum.shareit.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ItemRequestServiceTest {

    @Mock
    private ItemRequestJpaRepository repository;
    @Mock
    private ItemJpaRepository itemRepository;

    @InjectMocks
    private ItemRequestService service;

    @Test
    void createItemRequest() {

        Long userId = (long) 1;
        String description = "description";
        Long id = (long) 123;
        ItemRequestCreateDto crdto = new ItemRequestCreateDto();
        crdto.setDescription(description);
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(id);
        dto.setDescription(description);
        ItemRequest itemRequest = ItemRequest.builder()
                .id(id)
                .description(description)
                .build();

        Mockito.when(repository.save(any())).thenReturn(itemRequest);

        ItemRequestDto result = service.createItemRequest(crdto, userId);

        Assertions.assertEquals(result, dto);
    }

    @Test
    void getItemRequests() {
        Long userId = (long) 1;
        String description = "description";
        Long id = (long) 123;
        ItemRequest itemRequest = ItemRequest.builder()
                .id(id)
                .description(description)
                .build();
        ItemRequestDto dto = new ItemRequestDto();
        dto.setId(id);
        dto.setDescription(description);

        Mockito.when(repository.findAllByAuthorId(userId)).thenReturn(List.of(itemRequest));

        List<ItemRequestDto> result = service.getItemRequests(userId);

        Assertions.assertEquals(result.stream().findFirst().get(), dto);
    }

    @Test
    void getItemRequestByIdNull() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        ItemRequestDto result = service.getItemRequestById(1L, 1L);

        Assertions.assertNull(result);
    }

    @Test
    void getItemRequestById() {
        Long userId = (long) 1;
        String description = "description";
        Long id = (long) 123;
        String name = "name";
        boolean available = true;
        Item item = new Item();
        item.setName(name);
        item.setAvailable(available);
        item.setId(id);
        item.setUserId(userId);
        item.setDescription(description);
        ItemRequest itemRequest = ItemRequest.builder()
                .id(id)
                .description(description)
                .authorId(userId)
                .items(Set.of(id))
                .build();


        Mockito.when(repository.findById(any())).thenReturn(Optional.of(itemRequest));

        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        ItemRequestDto result = service.getItemRequestById(id, userId);

        Mockito.verify(itemRepository).findById(any());
    }
}