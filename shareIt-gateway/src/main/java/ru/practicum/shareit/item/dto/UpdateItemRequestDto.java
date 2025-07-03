package ru.practicum.shareit.item.dto;

import lombok.Data;
import ru.practicum.shareit.validation.NotBlankButNull;

@Data
public class UpdateItemRequestDto {
    @NotBlankButNull
    private String name;
    @NotBlankButNull
    private String description;
    private Boolean available;
}
