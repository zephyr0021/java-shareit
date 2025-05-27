package ru.practicum.shareit.user.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UserEmailValidator.class)

public @interface UniqueEmail {
    String message() default "a user with such an email already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
