package com.jeyofdev.yellow_berry.auth_user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    public List<AuthUser> findAll() throws AccessDeniedException {
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if(roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public AuthUser findUserByEmail(String email) throws AccessDeniedException, EntityNotFoundException {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (email == null || email.isEmpty()) {
            throw new ConstraintViolationException("The email field is required.", null);
        } else {
            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);

            if (!matcher.matches()) {
                throw new ConstraintViolationException("The email is not in the correct format.", null);
            }
        }

        if (username.equals(email) || roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("email " + email +" not found"));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public AuthUser findUserById(UUID userId) throws AccessDeniedException, EntityNotFoundException {
        UUID id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (id.equals(userId) || roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findById(userId).orElseThrow(
                    () -> new EntityNotFoundException("User with id " + userId + " not found")
            );
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }

    }
}