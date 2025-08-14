package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewItemRequestDto {
    @NotBlank(message = "must not be blank or null or empty")
    private String name;
    @NotBlank(message = "must not be blank or null or empty")
    private String description;
    @NotNull(message = "must not be blank or null or empty")
    private Boolean available;
    private Long requestId;
}
