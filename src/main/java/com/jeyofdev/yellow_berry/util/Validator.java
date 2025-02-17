package com.jeyofdev.yellow_berry.util;

import com.jeyofdev.yellow_berry.exception.BadValidationArgumentException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;

import java.util.regex.Pattern;

public class Validator {
    public static void emailFormat(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        if (!Pattern.matches(emailRegex, email)) {
            throw new ConstraintViolationException("The email is not in the correct format.", null);
        }
    }

    public static void passwordFormat(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,16}$";

        if (password == null || password.length() < 8) {
            throw new BadValidationArgumentException("The new password must contain at least 8 characters.");
        } else if (!Pattern.matches(passwordRegex, password)) {
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
