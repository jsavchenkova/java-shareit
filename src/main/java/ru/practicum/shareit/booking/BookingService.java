package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.constant.Status;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.exception.BookingNotFoundException;
import ru.practicum.shareit.booking.exception.UserIdException;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.ItemJpaRepository;
import ru.practicum.shareit.item.exception.ItemNotAvailableException;
import ru.practicum.shareit.item.exception.ItemNotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserJpaRepository;
import ru.practicum.shareit.user.exception.UserNotFoundException;
import ru.practicum.shareit.user.model.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingJpaRepository bookingRepository;
    private final UserJpaRepository userRepository;
    private final ItemJpaRepository itemRepository;

    public BookingDto createBooking(BookingCreateDto bookingDto, Long userId){
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserNotFoundException("Пользователь не найден");
        }
        Optional<Item> item = itemRepository.findById(bookingDto.getItemId());
        if(item.isEmpty()){
            throw new ItemNotFoundException("Вещь не существует");
        }
        if(!item.get().getAvailable()){
            throw new ItemNotAvailableException("Вещь недоступна для аренды");
        }
        Booking booking = BookingMapper.mapBookingCreateDtoToBooking(bookingDto);

        booking.setUser(user.get());
        booking.setItem(item.get());
        return BookingMapper.mapBookingToBoodingDto(bookingRepository.save(booking));
    }

    public BookingDto updateBooking(Long bookingId, Long userId, Boolean approval){
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        if(booking.isEmpty()){
            throw new BookingNotFoundException("Бронь не найдена");
        }
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new UserIdException("Доступ запрещён");
        }
        if(approval){
            booking.get().setStatus(Status.APPROVED);
        }else{
            booking.get().setStatus(Status.REJECTED);
        }
        return BookingMapper.mapBookingToBoodingDto(bookingRepository.save(booking.get()));
    }

    public List<BookingDto> findAll(){
        return bookingRepository.findAll().stream()
                .map(BookingMapper::mapBookingToBoodingDto)
                .sorted(Comparator.comparing(BookingDto::getId))
                .toList();
    }

    public List<BookingDto> findByOwner(Long userId){
        List<Booking>bookingList = bookingRepository.findByOwner(userId);
        if(bookingList== null || bookingList.size()==0){
            throw new BookingNotFoundException("Не найдено ни одного бронирования");
        }
        return bookingList.stream()
                .map(BookingMapper::mapBookingToBoodingDto)
                .sorted(Comparator.comparing(BookingDto::getId))
                .toList();
    }
}
