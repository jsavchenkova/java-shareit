package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {

    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> createItemRequest(@Valid @RequestBody ItemRequestCreateDto itemRequestCreateDto,
                                                    @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return requestClient.createItemRequest(itemRequestCreateDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemRequests(@Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return requestClient.getItemRequests(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemRequestById(@PathVariable long id,
                                                     @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return requestClient.getItemRequestById(id, userId);
    }
}
