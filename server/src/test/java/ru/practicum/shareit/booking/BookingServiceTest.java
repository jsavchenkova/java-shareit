package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.booking.constant.Status;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserJpaRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BookingServiceTest {

    private final BookingJpaRepository repository;
    private final ItemJpaRepository itemRepository;
    private final UserJpaRepository userRepositroy;


    @Test
    void createBooking() {
        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .item(new Item())
                .user(new User())
                .build();

        booking = repository.save(booking);

        Assertions.assertNotNull(booking.getId());
    }

    @Test
    void updateBooking() {
        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .item(new Item())
                .user(new User())
                .build();

        LocalDateTime newdate = LocalDateTime.now().plusDays(19);

        booking = repository.save(booking);
        booking.setEndDate(newdate);

        Booking result = repository.save(booking);

        Assertions.assertEquals(result.getEndDate(), newdate);
    }

    @Test
    void findAll() {
        Item item = new Item();
        item.setName("name");
        item = itemRepository.save(item);
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.dk");
        user = userRepositroy.save(user);
        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .item(item)
                .user(user)
                .build();

        booking = repository.save(booking);

        List<Booking> result = repository.findAll();

        Assertions.assertEquals(1, result.size());
    }


    @Test
    void findById() {
        Booking booking = Booking.builder()
                .status(Status.WAITING)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(6))
                .item(new Item())
                .user(new User())
                .build();

        booking = repository.save(booking);

        Optional<Booking> result = repository.findById(booking.getId());

        Assertions.assertTrue(result.isPresent());
    }
}