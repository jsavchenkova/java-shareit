package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.constant.Status;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.UserIdException;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.exception.ItemNotAvailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserJpaRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingJpaRepository repository;
    @Mock
    private UserJpaRepository userRepository;
    @Mock
    private ItemJpaRepository itemRepository;

    @InjectMocks
    private BookingService service;

    @Test
    void findById() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(BookingNotFoundException.class, () -> service.findById(1L, 2L));
    }

    @Test
    void updateBookingBookingNull() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(BookingNotFoundException.class, () -> service.updateBooking(1L, 2L, true));
    }

    @Test
    void updateBooking() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(new Booking()));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserIdException.class, () -> service.updateBooking(1L, 2L, true));
    }

    @Test
    void testUpdateBooking() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(new Booking()));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
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
        Mockito.when(repository.save(any())).thenReturn(booking);

        service.updateBooking(1L, 2L, true);

        Mockito.verify(repository).save(any());
    }

    @Test
    void testUpdateBookingNotApproval() {
        Mockito.when(repository.findById(any())).thenReturn(Optional.of(new Booking()));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
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
        Mockito.when(repository.save(any())).thenReturn(booking);

        service.updateBooking(1L, 2L, false);

        Mockito.verify(repository).save(any());
    }

    @Test
    void createBooking() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.createBooking(new BookingCreateDto(), 1L));
    }

    @Test
    void testCreateBooking() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> service.createBooking(new BookingCreateDto(), 1L));
    }

    @Test
    void testCreateBookingNotAvaliable() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        Item item = new Item();
        item.setAvailable(false);
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        Assertions.assertThrows(ItemNotAvailableException.class, () -> service.createBooking(new BookingCreateDto(), 1L));
    }

    @Test
    void testCreateBookingAvaliable() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        Item item = new Item();
        item.setAvailable(true);
        Mockito.when(itemRepository.findById(any())).thenReturn(Optional.of(item));

        long itemid = 1;
        long userId = 3;

        BookingCreateDto crdto = new BookingCreateDto();
        crdto.setItemId(itemid);
        crdto.setStart(LocalDateTime.now().toString());
        crdto.setEnd(LocalDateTime.now().plusDays(1).toString());

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

        Mockito.when(repository.save(any())).thenReturn(booking);

        service.createBooking(crdto, userId);

        Mockito.verify(repository).save(any());

    }

    @Test
    void findAll() {
        Item item = new Item();
        item.setAvailable(true);
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

        Mockito.when(repository.findAll()).thenReturn(List.of(booking));

        List<BookingDto> result = service.findAll();

        Assertions.assertEquals(booking.getId(), result.stream().findFirst().get().getId());
        Assertions.assertEquals(booking.getStatus().toString(), result.stream().findFirst().get().getStatus());
        Assertions.assertEquals(booking.getStartDate(), result.stream().findFirst().get().getStart());
        Assertions.assertEquals(booking.getEndDate(), result.stream().findFirst().get().getEnd());
        Assertions.assertEquals(booking.getUser().getId(), result.stream().findFirst().get().getBooker().getId());
        Assertions.assertEquals(booking.getItem().getId(), result.stream().findFirst().get().getItem().getId());
    }
}