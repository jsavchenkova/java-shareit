package ru.practicum.shareit.booking.dto;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.constant.Status;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@JsonTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingCreateDtoTest {
    private final JacksonTester<BookingCreateDto> json;

    @Test
    public void bookingCreateDtoTest() throws IOException {
        BookingCreateDto dto = new BookingCreateDto(
                1L,
                "2024-03-03 12:00:00",
                "2024-11-24 12:00:00",
                Status.WAITING
        );

        JsonContent<BookingCreateDto> result = json.write(dto);

        assertThat(result).extractingJsonPathNumberValue("$.itemId").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo("2024-03-03 12:00:00");
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo("2024-11-24 12:00:00");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo(Status.WAITING.toString());


    }

}