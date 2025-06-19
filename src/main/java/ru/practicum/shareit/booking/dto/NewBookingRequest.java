package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.serializer.OffsetDateTimeFromLocalDateTimeDeserializer;

import java.time.OffsetDateTime;

@Data
public class NewBookingRequest {

    @NotNull(message = "itemId must not be blank or null or empty")
    private Long itemId;

    @NotNull(message = "start must not be blank or null or empty")
    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    @FutureOrPresent
    private OffsetDateTime start;

    @NotNull(message = "start must not be blank or null or empty")
    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    @FutureOrPresent
    private OffsetDateTime end;
}
