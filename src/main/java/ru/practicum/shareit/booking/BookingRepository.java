package ru.practicum.shareit.booking;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.id = :id AND (b.booker.id = :userId OR b.item.owner.id = :userId)")
    Optional<BookingShort> findByItemOwnerOrBooker(@Param("id") Long id, @Param("userId") Long userId);

    Optional<BookingShort> findBookingById(Long id);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = :itemId " +
            "AND b.end < :now " +
            "AND b.status = ru.practicum.shareit.booking.model.BookingStatus.APPROVED")
    List<BookingShortForItem> findLastBookingForItem(@Param("itemId") Long itemId,
                                                     @Param("now") OffsetDateTime now, Pageable pageable);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = :itemId " +
            "AND b.start > :now " +
            "AND b.status = ru.practicum.shareit.booking.model.BookingStatus.APPROVED")
    List<BookingShortForItem> findNextBookingForItem(@Param("itemId") Long itemId,
                                                         @Param("now") OffsetDateTime now, Pageable pageable);

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

    Collection<BookingShort> findByItemOwnerId(Long ownerId, Sort sort);

    Collection<BookingShort> findByItemOwnerIdAndStatus(Long ownerId, BookingStatus status, Sort sort);

    Collection<BookingShort> findByItemOwnerIdAndStatusAndStartBeforeAndEndAfter(
            Long ownerId,
            BookingStatus status,
            OffsetDateTime start,
            OffsetDateTime end,
            Sort sort
    );

    Collection<BookingShort> findByItemOwnerIdAndStatusAndEndBefore(Long ownerId, BookingStatus status,
                                                                    OffsetDateTime end, Sort sort);

    Collection<BookingShort> findByItemOwnerIdAndStatusAndStartAfter(Long ownerId, BookingStatus status,
                                                                     OffsetDateTime start, Sort sort);
}
