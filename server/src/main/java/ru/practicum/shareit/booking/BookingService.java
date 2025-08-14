package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ServerException;
import ru.practicum.shareit.item.ItemValidationService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.UserValidationService;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ItemValidationService itemValidationService;
    private final UserValidationService userValidationService;
    @Value("${app.timezone}")
    private String timezone;

    public BookingDto getBookingById(Long id, Long userId) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return bookingRepository.findByItemOwnerOrBooker(id, userId)
                .map(BookingMapper::toBookingDto)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    public Collection<BookingDto> getAllBookingsByBooker(Long userId, String state) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        Collection<BookingShort> bookings = switch (state) {
            case "ALL" -> bookingRepository.findByBookerId(userId, sort);
            case "WAITING" -> bookingRepository.findByBookerIdAndStatus(userId, BookingStatus.WAITING, sort);
            case "REJECTED" -> bookingRepository.findByBookerIdAndStatus(userId, BookingStatus.REJECTED, sort);
            case "CURRENT" -> bookingRepository.findByBookerIdAndStatusAndStartBeforeAndEndAfter(userId,
                    BookingStatus.APPROVED, now, now, sort);
            case "PAST" ->
                    bookingRepository.findByBookerIdAndStatusAndEndBefore(userId, BookingStatus.APPROVED, now, sort);
            case "FUTURE" ->
                    bookingRepository.findByBookerIdAndStatusAndStartAfter(userId, BookingStatus.APPROVED, now, sort);
            default -> List.of();
        };

        return bookings.stream().map(BookingMapper::toBookingDto).toList();
    }

    public Collection<BookingDto> getAllBookingsByOwner(Long userId, String state) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        OffsetDateTime now = OffsetDateTime.now(ZoneId.of(timezone));
        Sort sort = Sort.by(Sort.Direction.DESC, "start");
        Collection<BookingShort> bookings = switch (state) {
            case "ALL" -> bookingRepository.findByItemOwnerId(userId, sort);
            case "WAITING" -> bookingRepository.findByItemOwnerIdAndStatus(userId, BookingStatus.WAITING, sort);
            case "REJECTED" -> bookingRepository.findByItemOwnerIdAndStatus(userId, BookingStatus.REJECTED, sort);
            case "CURRENT" -> bookingRepository.findByItemOwnerIdAndStatusAndStartBeforeAndEndAfter(userId,
                    BookingStatus.APPROVED, now, now, sort);
            case "PAST" ->
                    bookingRepository.findByItemOwnerIdAndStatusAndEndBefore(userId, BookingStatus.APPROVED, now, sort);
            case "FUTURE" ->
                    bookingRepository.findByItemOwnerIdAndStatusAndStartAfter(userId, BookingStatus.APPROVED, now, sort);
            default -> List.of();
        };

        return bookings.stream().map(BookingMapper::toBookingDto).toList();
    }

    @Transactional
    public BookingDto createBooking(NewBookingRequest request, Long userId) {
        User booker = userValidationService.isUserExistOrThrowNotFound(userId);
        Item item = itemValidationService.isItemExistOrThrowNotFound(request.getItemId());

        if (item.getAvailable().equals(Boolean.FALSE)) {
            throw new ServerException("Item is not available");
        }

        Booking booking = BookingMapper.toBooking(request, booker, item);

        booking.setStatus(BookingStatus.WAITING);

        bookingRepository.save(booking);

        return BookingMapper.toBookingDto(booking);
    }

    @Transactional
    public BookingDto approveBooking(Long id, Long userId, Boolean approved) {
        BookingStatus status = approved ? BookingStatus.APPROVED : BookingStatus.REJECTED;
        int updatedRows = bookingRepository.updateStatusByIdAndOwnerId(id, userId, status);

        if (updatedRows == 0) {
            throw new ServerException("Booking not updated");
        }

        return bookingRepository.findBookingById(id)
                .map(BookingMapper::toBookingDto)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }
}
