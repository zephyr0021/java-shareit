package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.user.validation.UniqueEmail;

@Data
public class NewUserRequest {
    private String name;
    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank or null or empty")
    @UniqueEmail
    private String email;
}
