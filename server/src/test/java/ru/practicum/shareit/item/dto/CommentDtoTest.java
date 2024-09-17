package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class CommentDtoTest {

    private final JacksonTester<BookingCreateDto> json;

    @Test
    public void commentDto() throws IOException {
        UserDto user = UserDto.builder().name("authorName").build();

        CommentDto dto = CommentDto.builder()
                .author(user)
                .build();

        assertThat(dto.getAuthorName()).isEqualTo(user.getName());

    }

}