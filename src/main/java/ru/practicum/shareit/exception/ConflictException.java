package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ConflictException extends RuntimeException {
    String name = "conflict data";

    public ConflictException(String message) {
        super(message);
    }
}
