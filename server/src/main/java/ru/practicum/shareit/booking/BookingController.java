package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    /**
     * Создание брони
     *
     * @param bookingDto
     * @param userId     автор брони
     * @return
     */
    @PostMapping
    public BookingDto createBookingDto(@RequestBody BookingCreateDto bookingDto,
                                        @RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return bookingService.createBooking(bookingDto, userId);

    }

    /**
     * Редактирование брони
     *
     * @param userId
     * @param id
     * @param approved
     * @return
     */
    @PatchMapping("/{id}")
    public BookingDto updateBooking(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                     @PathVariable long id,
                                     @RequestParam("approved") Boolean approved) {
        return bookingService.updateBooking(id, userId, approved);
    }

    /**
     * получение всех броней
     *
     * @return
     */
    @GetMapping
    public List<BookingDto> findAll() {
        return bookingService.findAll().reversed();
    }

    /**
     * Получение брони по хозяину
     *
     * @param userId
     * @return
     */
    @GetMapping("/owner")
    public List<BookingDto> findByOwner(@RequestHeader(name = "X-Sharer-User-Id") Long userId) {
        return bookingService.findByOwner(userId);
    }

    /**
     * получение брони по id
     *
     * @param userId
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public BookingDto findById(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                @PathVariable long id) {
        return bookingService.findById(id, userId);
    }

}
