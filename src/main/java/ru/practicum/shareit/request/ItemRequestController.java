package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final ItemRequestService service;

    @PostMapping
    public ItemRequestDto createItemRequest(@Valid @RequestBody ItemRequestCreateDto itemRequestCreateDto,
                                            @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return service.createItemRequest(itemRequestCreateDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getItemRequests(@Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return service.getItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ItemRequestDto getItemRequestById(@PathVariable long id,
                                             @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return service.getItemRequestById(id, userId);
    }
}
