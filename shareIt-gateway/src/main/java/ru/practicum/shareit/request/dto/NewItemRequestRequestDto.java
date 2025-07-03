package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewItemRequestRequestDto {
    @NotBlank(message = "description must not be blank or null or empty")
    private String description;
}
