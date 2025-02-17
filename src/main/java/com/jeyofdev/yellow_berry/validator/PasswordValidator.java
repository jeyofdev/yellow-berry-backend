package com.jeyofdev.yellow_berry.validator;

import com.jeyofdev.yellow_berry.annotation.ValidPassword;
import com.jeyofdev.yellow_berry.core.constant.Regex;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
    private int min;
    private int max;
    private String requiredMessage;
    private String lengthMessage;
    private String formatMessage;

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        requiredMessage = constraintAnnotation.requiredMessage();
        lengthMessage = constraintAnnotation.lengthMessage();
        formatMessage = constraintAnnotation.formatMessage();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            addConstraintViolation(context, requiredMessage);
            return false;
        }

        if (value.length() < min || value.length() > max) {
            addConstraintViolation(context, lengthMessage);
            return false;
        }


        if (!value.matches(Regex.PASSWORD_PATTERN)) {
            addConstraintViolation(context, formatMessage);
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
