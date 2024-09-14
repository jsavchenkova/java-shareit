package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto extends BookingCreateDto {
    private Long id;
    private String text;
    private ItemDto itemDto;
    private UserDto author;
    private String authorName;
    private LocalDateTime created;

    public String getAuthorName() {
        if (author != null) {
            return author.getName();
        }
        return null;
    }
}
