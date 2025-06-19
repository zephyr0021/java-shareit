package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
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
import ru.practicum.shareit.user.model.UserValidationService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {
    private final BookingRepository bookingRepository;
    private final ItemValidationService itemValidationService;
    private final UserValidationService userValidationService;

    public BookingDto getBookingById(Long id, Long userId) {
        userValidationService.isUserExistOrThrowNotFound(userId);
        return bookingRepository.findByItemOwnerOrBooker(id, userId)
                .map(BookingMapper::toBookingDto)
                .orElseThrow(() -> new NotFoundException("Booking not found"));
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
}
