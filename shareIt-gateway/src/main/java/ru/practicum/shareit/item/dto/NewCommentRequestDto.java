package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewCommentRequestDto {
    @NotBlank(message = "text must not be blank or null or empty")
    private String text;
}
