package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemRequestService {

    private final ItemRequestJpaRepository itemRequestJpaRepository;
    private final ItemJpaRepository itemRepository;

    public ItemRequestDto createItemRequest(ItemRequestCreateDto itemRequestCreateDto, Long userId) {
        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(itemRequestCreateDto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setAuthorId(userId);
        return ItemRequestMapper.mapToItemRequestDto(itemRequestJpaRepository.save(itemRequest));
    }

    public List<ItemRequestDto> getItemRequests(Long userId) {
        List<ItemRequest> list = itemRequestJpaRepository.findAllByAuthorId(userId);
        List<ItemRequestDto> result = list.stream()
                .map(ItemRequestMapper::mapToItemRequestDto)
                .toList();
        return result;
    }

    public ItemRequestDto getItemRequestById(Long id, Long userId) {
        Optional<ItemRequest> itemRequest = itemRequestJpaRepository.findById(id);
        if (itemRequest.isPresent()) {
            ItemRequestDto res = ItemRequestMapper.mapToItemRequestDto(itemRequest.get());
            List<ItemDto> list = itemRequest.get().getItems().stream()
                    .map(x -> itemRepository.findById(x).get())
                    .map(ItemMapper::mapItemToItemDto)
                    .toList();
            res.setItems(list);
            return res;
        }
        return null;
    }
}
