package ru.practicum.shareit.booking.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.constant.Status;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingMapperTest {


    @Test
    void mapBookingCreateDtoToBooking() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusDays(5);

        BookingCreateDto crdto = new BookingCreateDto();
        crdto.setStart(start.toString());
        crdto.setEnd(end.toString());


        Booking result = BookingMapper.mapBookingCreateDtoToBooking(crdto);

        Assertions.assertEquals(start, result.getStartDate());
        Assertions.assertEquals(end, result.getEndDate());
    }

    @Test
    void mapBookingToBoodingDto() {

        Item item = new Item();
        item.setId(1L);
        item.setName("name");
        User user = new User();
        user.setId(2L);
        user.setName("name");
        user.setEmail("email@email.dk");
        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .item(item)
                .user(user)
                .id(3L)
                .build();

        BookingDto result = BookingMapper.mapBookingToBoodingDto(booking);

        Assertions.assertEquals(booking.getId(), result.getId());
        Assertions.assertEquals(booking.getStartDate(), result.getStart());
        Assertions.assertEquals(booking.getEndDate(), result.getEnd());
        Assertions.assertEquals(booking.getUser().getName(), result.getBooker().getName());
        Assertions.assertEquals(booking.getItem().getId(), result.getItem().getId());

    }
}