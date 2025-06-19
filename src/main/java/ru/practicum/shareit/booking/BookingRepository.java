package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(
            "SELECT b FROM Booking b " +
                    "JOIN FETCH b.item i " +
                    "WHERE b.id = :id " +
                    "AND (b.booker.id = :userId OR i.owner.id = :userId)"
    )
    Optional<BookingShort> findByItemOwnerOrBooker(@Param("id") Long id, @Param("userId") Long userId);
}
