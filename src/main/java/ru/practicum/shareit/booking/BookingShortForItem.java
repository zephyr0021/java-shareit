package ru.practicum.shareit.booking;

import java.time.OffsetDateTime;

public interface BookingShortForItem {
    Long getId();

    UserInfo getBooker();

    OffsetDateTime getStart();

    OffsetDateTime getEnd();

    interface UserInfo {
        Long getId();
    }

}
