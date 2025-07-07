package ru.practicum.shareit.user.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequestDto {
    @NotBlank(message = "must not be blank or null or empty")
    private String name;
    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank or null or empty")
    private String email;
}
