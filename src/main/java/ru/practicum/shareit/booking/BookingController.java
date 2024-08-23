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

    @PostMapping
    private BookingDto createBookingDto(@RequestBody BookingCreateDto bookingDto,
                                        @RequestHeader(name = "X-Sharer-User-Id") Long userId){
         return bookingService.createBooking(bookingDto, userId);

    }

    @PatchMapping("/{id}")
    private BookingDto updateBooking(@RequestHeader(name = "X-Sharer-User-Id") Long userId,
                                     @PathVariable long id,
                                     @RequestParam("approved") Boolean approved){
        return bookingService.updateBooking(id,userId, approved);
    }

    @GetMapping
    private List<BookingDto> findAll(){
        return bookingService.findAll().reversed();
    }

    @GetMapping("/{owner}")
    private List<BookingDto> findByOwner(@RequestHeader(name = "X-Sharer-User-Id") Long userId){
        return bookingService.findByOwner(userId);
    }

}
