package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.ItemValidationService;
import ru.practicum.shareit.user.UserValidationService;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DataJpaTest
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Import({BookingService.class, ItemValidationService.class, UserValidationService.class})
public class BookingIntegrationTests {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ItemValidationService itemValidationService;
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void createBooking() {
        NewBookingRequest request = new NewBookingRequest(2L, OffsetDateTime.now().plusHours(1), OffsetDateTime.now().plusHours(2));
        BookingDto booking = bookingService.createBooking(request, 2L);
        Optional<BookingShort> createdBooking = bookingRepository.findBookingById(booking.getId());
        assertTrue(createdBooking.isPresent());
        assertEquals(4L, createdBooking.get().getId());
        assertEquals(request.getStart().truncatedTo(ChronoUnit.SECONDS).toInstant(), createdBooking.get().getStart().truncatedTo(ChronoUnit.SECONDS).toInstant());
        assertEquals(request.getEnd().truncatedTo(ChronoUnit.SECONDS).toInstant(), createdBooking.get().getEnd().truncatedTo(ChronoUnit.SECONDS).toInstant());
    }

    @Test
    void getAllBookingsByBookerStateAll() {
        Booking booking1 = new Booking();
        booking1.setStart(OffsetDateTime.now().plusHours(1));
        booking1.setEnd(OffsetDateTime.now().plusHours(2));
        booking1.setStatus(BookingStatus.WAITING);
        booking1.setBooker(userValidationService.isUserExistOrThrowNotFound(2L));
        booking1.setItem(itemValidationService.isItemExistOrThrowNotFound(2L));

        Booking booking2 = new Booking();
        booking2.setStart(OffsetDateTime.now().plusHours(3));
        booking2.setEnd(OffsetDateTime.now().plusHours(5));
        booking2.setStatus(BookingStatus.APPROVED);
        booking2.setBooker(userValidationService.isUserExistOrThrowNotFound(2L));
        booking2.setItem(itemValidationService.isItemExistOrThrowNotFound(2L));

        Booking savedBooking1 = bookingRepository.save(booking1);
        Booking savedBooking2 = bookingRepository.save(booking2);
        ArrayList<BookingDto> bookingList = new ArrayList<>(bookingService.getAllBookingsByBooker(2L, "ALL"));
        assertFalse(bookingList.isEmpty());
        assertEquals(savedBooking2.getId(), bookingList.get(0).getId());
        assertEquals(savedBooking1.getId(), bookingList.get(1).getId());
    }

    @Test
    void getAllBookingsByBookerStateWaiting() {
        Booking booking1 = new Booking();
        booking1.setStart(OffsetDateTime.now().plusHours(1));
        booking1.setEnd(OffsetDateTime.now().plusHours(2));
        booking1.setStatus(BookingStatus.WAITING);
        booking1.setBooker(userValidationService.isUserExistOrThrowNotFound(2L));
        booking1.setItem(itemValidationService.isItemExistOrThrowNotFound(2L));

        Booking booking2 = new Booking();
        booking2.setStart(OffsetDateTime.now().plusHours(3));
        booking2.setEnd(OffsetDateTime.now().plusHours(5));
        booking2.setStatus(BookingStatus.APPROVED);
        booking2.setBooker(userValidationService.isUserExistOrThrowNotFound(2L));
        booking2.setItem(itemValidationService.isItemExistOrThrowNotFound(2L));

        Booking savedBooking1 = bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        ArrayList<BookingDto> bookingList = new ArrayList<>(bookingService.getAllBookingsByBooker(2L, "WAITING"));
        assertFalse(bookingList.isEmpty());
        assertEquals(1, bookingList.size());
        assertEquals(savedBooking1.getId(), bookingList.getFirst().getId());
    }

}
