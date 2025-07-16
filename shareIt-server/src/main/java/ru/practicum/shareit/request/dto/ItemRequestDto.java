package ru.practicum.shareit.request.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDtoForItemRequest;
import ru.practicum.shareit.serializer.OffsetDateTimeToLocalDateTimeSerializer;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    private String description;

    @JsonSerialize(using = OffsetDateTimeToLocalDateTimeSerializer.class)
    private OffsetDateTime created;

    private List<ItemDtoForItemRequest> items;


}
