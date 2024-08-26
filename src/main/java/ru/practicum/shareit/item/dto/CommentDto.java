package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {
    Long id;
    String text;
    ItemDto itemDto;
    UserDto author;
    String authorName;
    LocalDateTime created;

    public String getAuthorName() {
        if(author != null){
            return author.getName();
        }
        return null;
    }
}
