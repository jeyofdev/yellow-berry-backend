package com.jeyofdev.yellow_berry.util;

import com.jeyofdev.yellow_berry.core.constant.Regex;
import com.jeyofdev.yellow_berry.exception.BadValidationArgumentException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;

import java.util.regex.Pattern;

public class Validator {
    public static void emailFormat(String email) {
        if (!Pattern.matches(Regex.EMAIL_PATTERN, email)) {
            throw new ConstraintViolationException("The email is not in the correct format.", null);
        }
    }

    public static void passwordFormat(String password) {
        if (password == null || password.length() < 8) {
            throw new BadValidationArgumentException("The new password must contain at least 8 characters.");
        } else if (!Pattern.matches(Regex.PASSWORD_PATTERN, password)) {
            throw new ConstraintViolationException("The password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character.", null);
        }
    }

    /**
     * Check if validation errors exists
     */
    public static void checkValidationErrorsExist(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append("; "));

            throw new ConstraintViolationException(errors.toString(), null);
        }
    }
}
