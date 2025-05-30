package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.user.validation.UniqueEmail;

@Data
public class NewUserRequest {
    @NotBlank(message = "name must not be blank or null or empty")
    private String name;
    @Email(message = "email must be a well-formed email address")
    @NotBlank(message = "email must not be blank or null or empty")
    @UniqueEmail
    private String email;
}
