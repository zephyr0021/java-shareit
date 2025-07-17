package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class AccessDeniedException extends RuntimeException {
    String name = "access denied";

    public AccessDeniedException(String message) {
        super(message);
    }
}
