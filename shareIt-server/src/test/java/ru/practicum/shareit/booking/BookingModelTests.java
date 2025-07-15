package ru.practicum.shareit.booking;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingModelTests {

    @Test
    void testEqualsAndHashCode() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStart(OffsetDateTime.now().plusHours(1));
        booking.setEnd(OffsetDateTime.now().plusHours(2));
        booking.setStatus(BookingStatus.WAITING);
        booking.setBooker(new User());
        booking.setItem(new Item());

        Booking booking2 = new Booking();
        booking2.setId(1L);
        booking2.setStart(OffsetDateTime.now().plusHours(4));
        booking.setEnd(OffsetDateTime.now().plusHours(6));
        booking.setStatus(BookingStatus.APPROVED);

        assertEquals(booking, booking2);
        assertEquals(booking.hashCode(), booking2.hashCode());
    }
}
