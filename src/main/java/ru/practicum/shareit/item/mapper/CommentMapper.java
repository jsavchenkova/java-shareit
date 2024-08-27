package ru.practicum.shareit.item.mapper;

import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;

public class CommentMapper {
    public static CommentDto mapCommentToCommentDto(Comment comment) {
        ItemDto item = ItemDto.builder()
                .id(comment.getItem().getId())
                .name(comment.getItem().getName())
                .description(comment.getItem().getDescription())
                .userId(comment.getItem().getUserId())
                .available(comment.getItem().getAvailable())
                .build();
        UserDto user = UserMapper.mapUserToUserDto(comment.getAuthor());
        return CommentDto.builder()
                .id(comment.getId())
                .itemDto(item)
                .text(comment.getText())
                .created(comment.getCreated())
                .author(user)
                .build();
    }

    public static Comment mapCommentCreateDtotoComment(CommentCreateDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        return comment;
    }
}
