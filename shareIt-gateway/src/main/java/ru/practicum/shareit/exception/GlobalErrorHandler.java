package ru.practicum.shareit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.practicum.shareit.response.ErrorResponse;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .toList();
        String message = String.join(", ", errors);
        log.warn("Ошибка валидации: {}", errors);

        return new ErrorResponse("validation error", message);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequestHeaderException(final MissingRequestHeaderException e) {
        log.warn("Не передан обязательный хэдер: {}", e.getMessage());
        return new ErrorResponse("missing required header",
                String.format("Required request header %s is not present", e.getHeaderName()));
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingRequestHeaderException(final MissingServletRequestParameterException e) {
        log.warn("Не передан обязательный параметр: {}", e.getMessage());
        return new ErrorResponse("missing required parameter",
                String.format("Required request parameter %s is not present", e.getParameterName()));
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        log.warn("Передан не валидный параметр: {}", e.getMessage());
        return new ErrorResponse("bad parameter value",
                String.format("Bad parameter %s value", e.getName()));
    }



}
