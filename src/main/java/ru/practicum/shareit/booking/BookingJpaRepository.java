package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingJpaRepository extends JpaRepository<Booking, Long> {
    @Query("select new ru.practicum.shareit.booking.Booking(b.id, b.user, b.item, b.startDate, b.endDate, b.status) " +
            "from Booking  b, Item i " +
            "where b.item.id = i.id " +
            "and b.user.id = ?1"
    )
    List<Booking> findByOwner(Long id);

    @Query("select new ru.practicum.shareit.booking.Booking(b.id, b.user, b.item, b.startDate, b.endDate, b.status) " +
            "from Booking b " +
            "where b.item.id = ?1 " +
            "and b.endDate < current_date " +
            "order by b.endDate desc "
    )
    List<Booking> getLasBooking(Long id);

    @Query("select new ru.practicum.shareit.booking.Booking(b.id, b.user, b.item, b.startDate, b.endDate, b.status) " +
            "from Booking b " +
            "where b.item.id = ?1 " +
            "and b.startDate < current_date " +
            "order by b.startDate "
    )
    List<Booking> getNextBooking(Long id);
}
