package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewUserRequestDto {
    @NotBlank(message = "name must not be blank or null or empty")
    private String name;
    @Email(message = "email must be a well-formed email address")
    @NotBlank(message = "email must not be blank or null or empty")
    private String email;
}
