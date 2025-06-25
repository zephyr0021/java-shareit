package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.OffsetDateTime;

public interface BookingShort {
    Long getId();

    OffsetDateTime getStart();

    OffsetDateTime getEnd();

    BookingStatus getStatus();

    UserInfo getBooker();

    ItemInfo getItem();

    interface UserInfo {
        Long getId();
    }

    interface ItemInfo {
        Long getId();

        String getName();
    }
}
