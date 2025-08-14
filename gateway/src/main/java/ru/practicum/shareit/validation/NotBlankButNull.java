package ru.practicum.shareit.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Constraint(validatedBy = NotBlankButNullValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBlankButNull {
    String message() default "must not be blank if present";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
