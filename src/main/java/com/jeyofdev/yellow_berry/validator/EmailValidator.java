package com.jeyofdev.yellow_berry.validator;

import com.jeyofdev.yellow_berry.annotation.ValidEmail;
import com.jeyofdev.yellow_berry.core.constant.Regex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            addConstraintViolation(context, "The email field is required.");
            return false;
        }

        if (value.length() > 100) {
            addConstraintViolation(context, "The email must be at most 100 characters.");
            return false;
        }

        if (!value.matches(Regex.EMAIL_PATTERN)) {
            addConstraintViolation(context, "The email is not in the correct format.");
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}

