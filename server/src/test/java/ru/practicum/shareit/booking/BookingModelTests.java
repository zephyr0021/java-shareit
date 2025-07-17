package ru.practicum.shareit.booking;

import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;
import ru.practicum.shareit.booking.model.Booking;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingModelTests {

    @Test
    void testEqualsAndHashCode() {
        Model<Booking> model = Instancio.of(Booking.class)
                .set(Select.field("id"), 1L)
                .toModel();
        Booking booking = Instancio.create(model);
        Booking booking2 = Instancio.create(model);

        assertEquals(booking, booking2);
        assertEquals(booking.hashCode(), booking2.hashCode());
    }
}
