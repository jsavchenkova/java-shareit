package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
    Long id;
    String text;
    ItemDto itemDto;
}
