package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.shareit.serializer.OffsetDateTimeFromLocalDateTimeDeserializer;
import ru.practicum.shareit.serializer.OffsetDateTimeToLocalDateTimeSerializer;

import java.time.OffsetDateTime;

@Data
public class NewBookingRequestDto {
    @NotNull
    private Long itemId;
    @FutureOrPresent
    @NotNull
    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime start;
    @Future
    @NotNull
    @JsonDeserialize(using = OffsetDateTimeFromLocalDateTimeDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime end;
}
