package com.jeyofdev.yellow_berry.annotation;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
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
    String requiredMessage() default ErrorMessage.PASSWORD_REQUIRED;
    String lengthMessage() default ErrorMessage.PASSWORD_LENGTH;
    String formatMessage() default ErrorMessage.PASSWORD_FORMAT;
    int min() default 8;
    int max() default 16;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}