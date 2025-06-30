package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.practicum.shareit.serializer.OffsetDateTimeFromLocalDateTimeDeserializer;

import java.time.OffsetDateTime;

@Data
public class NewBookingRequest {

    private Long itemId;

    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    private OffsetDateTime start;

    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    private OffsetDateTime end;
}
