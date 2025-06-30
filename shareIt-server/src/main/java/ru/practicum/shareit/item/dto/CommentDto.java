package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import ru.practicum.shareit.serializer.OffsetDateTimeToLocalDateTimeSerializer;

import java.time.OffsetDateTime;

@Data
public class CommentDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String text;
    private String authorName;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime created;
}
