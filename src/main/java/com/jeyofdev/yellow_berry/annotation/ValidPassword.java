package com.jeyofdev.yellow_berry.annotation;

import com.jeyofdev.yellow_berry.validator.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
    String message() default "Invalid password";
    String requiredMessage() default "The password field is required.";
    String lengthMessage() default "The password must be contain between 8 and 16 characters.";
    String formatMessage() default "The password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.";
    int min() default 8;
    int max() default 16;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}