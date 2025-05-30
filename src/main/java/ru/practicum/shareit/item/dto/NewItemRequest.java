package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewItemRequest {
    @NotBlank(message = "name must not be blank or null or empty")
    private String name;
    @NotBlank(message = "description must not be blank or null or empty")
    private String description;
    @NotNull(message = "available must not be blank or null or empty")
    private Boolean available;
}
