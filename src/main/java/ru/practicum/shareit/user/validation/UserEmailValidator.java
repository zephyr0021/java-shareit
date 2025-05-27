package ru.practicum.shareit.user.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ConflictException;
import ru.practicum.shareit.user.UserRepository;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean exists = userRepository.existsByEmail(email);

        if (!exists) {
            return true;
        } else {
            log.warn("User {} already exists", email);
            throw new ConflictException("User " + email + " already exists");
        }
    }
}
