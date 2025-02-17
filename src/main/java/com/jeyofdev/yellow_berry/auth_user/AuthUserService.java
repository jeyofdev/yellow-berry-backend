package com.jeyofdev.yellow_berry.auth_user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;
import java.util.UUID;

public interface AuthUserService {
    List<AuthUser> findAll() throws AccessDeniedException;

    AuthUser findUserByEmail(String email) throws AccessDeniedException, ConstraintViolationException, EntityNotFoundException;

    AuthUser findUserById(UUID userId) throws AccessDeniedException, EntityNotFoundException;
}
