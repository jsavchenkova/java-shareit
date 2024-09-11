package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoCreate;
import ru.practicum.shareit.item.dto.ItemDtoUpdate;

import java.util.List;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                        .build()
        );
    }

    public ResponseEntity<Object> createItem(ItemDtoCreate itemDto,
                                              Long userId){
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object>  updateItem(long id, ItemDtoUpdate itemDto,
                                               Long userId){
        return patch("/" +id, userId,itemDto);
    }

    public ResponseEntity<Object> getItems(Long userId){
        return get("", userId);
    }

    public ResponseEntity<Object> getItemById(long id){
        return get("/" + id);
    }

    public ResponseEntity<Object> searchItem(String text,
                                    Long userId){
        return get("/search?text=" + text, userId);
    }

    public ResponseEntity<Object> deleteItem(long id){
        return delete("/" + id);
    }

    public ResponseEntity<Object> createComment(Long userId,
                                                Long itemId, CommentCreateDto commentText){
        return post("/" + itemId +"/comment", userId, commentText);
    }
}
