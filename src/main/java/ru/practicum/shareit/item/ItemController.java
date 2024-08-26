package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    /**
     * Добавление вещи
     * @param itemDto
     * @param userId
     * @return
     */
    @PostMapping
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDtoCreate itemDto,
                                              @Valid @NotNull @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        userService.getUserById(userId);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Sharer-User-Id", userId.toString());
        return new ResponseEntity<ItemDto>(itemService.createItem(itemDto, userId), responseHeaders, HttpStatus.OK);
    }

    /**
     * Изменение вещи
     * @param id
     * @param itemDto
     * @param userId
     * @return
     */
    @PatchMapping("/{id}")
    public ItemDto updateItem(@PathVariable long id, @RequestBody ItemDtoUpdate itemDto,
                              @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        userService.getUserById(userId);
        return itemService.updateItem(id, itemDto);
    }

    /**
     * Получение списка вещей одного пользователя
     * @param userId
     * @return
     */
    @GetMapping
    public List<ItemDto> getItems(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return itemService.getItems(userId);
    }

    /**
     * Получение вещи по id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ItemDto getItemById(@PathVariable long id) {
        return itemService.getItemById(id);
    }

    /**
     * Поиск вещи по названию или описанию
     * @param text
     * @param userId
     * @return
     */
    @GetMapping("/search")
    public List<ItemDto> searchItem(@RequestParam("text") String text,
                                    @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        userService.getUserById(userId);
        return itemService.searchItem(text);
    }

    /**
     * Удаление вещи
     * @param id
     */
    @DeleteMapping("/{id}")
    public void deleteItem(long id) {
        itemService.deleteItem(id);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                    @PathVariable Long itemId, @RequestBody CommentCreateDto commentText){
        return itemService.createComment(commentText, userId, itemId);
    }
}
