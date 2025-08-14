package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.validation.NotBlankButNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateItemRequestDto {
    @NotBlankButNull
    private String name;
    @NotBlankButNull
    private String description;
    private Boolean available;
}
