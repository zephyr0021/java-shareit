package ru.practicum.shareit.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
  String name = "server error";

  public ServerException(String message) {
        super(message);
    }
}
