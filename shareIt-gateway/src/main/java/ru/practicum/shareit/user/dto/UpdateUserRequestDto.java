package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;
import ru.practicum.shareit.validation.NotBlankButNull;

@Data
public class UpdateUserRequestDto {
    @NotBlankButNull
    private String name;
    @Email
    @NotBlankButNull
    private String email;
}
