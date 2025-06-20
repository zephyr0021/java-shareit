package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query(
            "SELECT b FROM Booking b " +
                    "JOIN FETCH b.item i " +
                    "WHERE b.id = :id " +
                    "AND (b.booker.id = :userId OR i.owner.id = :userId)"
    )
    Optional<BookingShort> findByItemOwnerOrBooker(@Param("id") Long id, @Param("userId") Long userId);

    Optional<BookingShort> findBookingById(Long id);

    @Modifying
    @Query("UPDATE Booking b SET b.status = :status WHERE b.id = :id AND b.item.owner.id = :ownerId")
    int updateStatusByIdAndOwnerId(@Param("id") Long id, @Param("ownerId") Long ownerId,
                                   @Param("status") BookingStatus status);

    Collection<BookingShort> findByBookerId(Long bookerId, Sort sort);

    Collection<BookingShort> findByBookerIdAndStatusAndStartBeforeAndEndAfter(
            Long bookerId,
            BookingStatus status,
            OffsetDateTime start,
            OffsetDateTime end,
            Sort sort
    );

    Collection<BookingShort> findByBookerIdAndStatusAndEndBefore(Long bookerId, BookingStatus status,
                                                                 OffsetDateTime end, Sort sort);

    Collection<BookingShort> findByBookerIdAndStatus(Long bookerId, BookingStatus status, Sort sort);

    Collection<BookingShort> findByBookerIdAndStatusAndStartAfter(Long bookerId, BookingStatus status,
                                                                  OffsetDateTime start, Sort sort);
}
