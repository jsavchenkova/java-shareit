package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.shareit.booking.constant.Status;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingCreateDto {
    private Long itemId;
    private String start;
    private String end;
    private Status status = Status.WAITING;
}
