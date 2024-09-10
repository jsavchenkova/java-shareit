package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemRequestService {

    private final ItemRequestJpaRepository itemRequestJpaRepository;
    private final ItemJpaRepository itemRepository;

    public ItemRequestDto createItemRequest(ItemRequestCreateDto itemRequestCreateDto, Long user_id){
        ItemRequest itemRequest = ItemRequestMapper.mapToItemRequest(itemRequestCreateDto);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setAuthorId(user_id);
        return ItemRequestMapper.mapToItemRequestDto(itemRequestJpaRepository.save(itemRequest));
    }

    public List<ItemRequestDto> getItemRequests (Long user_id){
        List<ItemRequest> list = itemRequestJpaRepository.findAllByAuthorId(user_id);
        List<ItemRequestDto> result = list.stream()
                .map(ItemRequestMapper::mapToItemRequestDto)
                .toList();
        return result;
    }

    public ItemRequestDto getItemRequestById(Long id, Long user_id){
        Optional<ItemRequest> itemRequest = itemRequestJpaRepository.findById(id);
        if(itemRequest.isPresent()){
            ItemRequestDto res =  ItemRequestMapper.mapToItemRequestDto(itemRequest.get());
            List<ItemDto>list = itemRequest.get().items.stream()
                    .map(x -> itemRepository.findById(x).get())
                    .map(ItemMapper::mapItemToItemDto)
                    .toList();
            res.setItems(list);
            return res;
        }
        return null;
    }
}
