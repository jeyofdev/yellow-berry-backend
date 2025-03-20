package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.auth.model.*;
import com.jeyofdev.yellow_berry.exception.BadValidationArgumentException;
import com.jeyofdev.yellow_berry.exception.ExpireTokenException;
import com.jeyofdev.yellow_berry.exception.InvalidTokenException;
import com.jeyofdev.yellow_berry.exception.EmailAlreadyTakenException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;

public interface AuthService {
    RegisterResponse register(RegisterRequest request, BindingResult bindingResult) throws IllegalArgumentException, EmailAlreadyTakenException;

    AuthResponse login(LoginRequest request, BindingResult bindingResult) throws ConstraintViolationException, BadCredentialsException;

    MessageResponse validateAccount(String verificationToken) throws InvalidTokenException, ExpireTokenException;

    MessageResponse updatePassword(String oldPassword, String newPassword, BindingResult bindingResult) throws IllegalStateException, BadValidationArgumentException, UsernameNotFoundException, AccessDeniedException;

    MessageResponse requestPasswordReset(String email) throws ConstraintViolationException, UsernameNotFoundException;

    MessageResponse resetPassword(String token, String newPassword) throws IllegalStateException, ExpireTokenException, BadValidationArgumentException;
}
