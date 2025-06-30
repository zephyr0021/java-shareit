package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.serializer.OffsetDateTimeToLocalDateTimeSerializer;

import java.time.OffsetDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class BookingDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime start;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime end;
    private BookingStatus status;
    private Booker booker;
    private Item item;

    @Data
    public static class Booker {
        private Long id;
    }

    @Data
    public static class Item {
        private Long id;
        private String name;
    }


}
