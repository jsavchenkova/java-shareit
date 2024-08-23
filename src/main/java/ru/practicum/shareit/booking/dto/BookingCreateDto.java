package ru.practicum.shareit.booking.dto;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.booking.constant.Status;

import java.time.ZonedDateTime;

@Getter
@Setter
public class BookingCreateDto {
    private Long itemId;
    private String start;
    private String end;
    private Status status = Status.WAITING;
}
