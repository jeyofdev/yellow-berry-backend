package com.jeyofdev.yellow_berry.auth_user;

import com.jeyofdev.yellow_berry.core.constant.Regex;
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
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Override
    public List<AuthUser> findAll() throws AccessDeniedException {
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if(roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    @Override
    public AuthUser findUserByEmail(String email) throws AccessDeniedException, ConstraintViolationException, EntityNotFoundException {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (email == null || email.isEmpty()) {
            throw new ConstraintViolationException("The email field is required.", null);
        } else {
            if (!Pattern.matches(Regex.EMAIL_PATTERN, email)) {
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

    @Override
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