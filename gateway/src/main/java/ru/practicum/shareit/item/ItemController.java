package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemDtoCreate itemDto,
                                              @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return itemClient.createItem(itemDto, userId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object>  updateItem(@PathVariable long id, @RequestBody ItemDtoUpdate itemDto,
                              @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return itemClient.updateItem(id, itemDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getItems(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return itemClient.getItems(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable long id) {
        return itemClient.getItemById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchItem(@RequestParam("text") String text,
                                    @RequestHeader(name = "X-Sharer-User-Id") Long userId) {

        return itemClient.searchItem(text, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteItem(long id) {
        return itemClient.deleteItem(id);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                    @PathVariable Long itemId, @RequestBody CommentCreateDto commentText) {
        return itemClient.createComment(userId, itemId, commentText);
    }
}
