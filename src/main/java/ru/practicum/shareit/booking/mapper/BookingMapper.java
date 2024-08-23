package ru.practicum.shareit.booking.mapper;

import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class BookingMapper {
    public static Booking mapBookingCreateDtoToBooking (BookingCreateDto bookingCreateDto){

        Booking booking = new Booking();
        booking.setStartDate(LocalDateTime.parse(bookingCreateDto.getStart()));
        booking.setEndDate(LocalDateTime.parse(bookingCreateDto.getEnd()));
        booking.setStatus(bookingCreateDto.getStatus());
        return booking;
    }

    public static BookingDto mapBookingToBoodingDto (Booking booking){
        UserDto userDto = UserDto.builder()
                .id(booking.getUser().getId())
                .name(booking.getUser().getName())
                .build();
        return BookingDto.builder()
                .booker(userDto)
                .id(booking.getId())
                .item(ItemDto.builder()
                        .id(booking.getItem().getId())
                        .name(booking.getItem().getName())
                        .build())
                .end(booking.getEndDate())
                .start(booking.getStartDate())
                .status(booking.getStatus().name())
                .build();
    }

}
