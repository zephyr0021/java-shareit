package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.practicum.shareit.serializer.OffsetDateTimeToLocalDateTimeSerializer;

import java.time.OffsetDateTime;

@Data
public class BookingForItemDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private Long bookerId;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime start;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime end;
}
