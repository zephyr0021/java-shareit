package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {
    String name = "not found";

    public NotFoundException(String message) {
        super(message);
    }
}
