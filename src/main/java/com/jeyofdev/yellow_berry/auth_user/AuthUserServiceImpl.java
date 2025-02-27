package com.jeyofdev.yellow_berry.auth_user;

import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.constant.Regex;
import com.jeyofdev.yellow_berry.security.util.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthUserServiceImpl implements AuthUserService {

    private final AuthUserRepository authUserRepository;

    @Override
    public List<AuthUser> findAll() throws AccessDeniedException {
        if(SecurityUtil.getAuthenticatedRole().equals("[ROLE_ADMIN]")) {
            return authUserRepository.findAll();
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    @Override
    public AuthUser findUserByEmail(String email) throws AccessDeniedException, ConstraintViolationException, EntityNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new ConstraintViolationException(ErrorMessage.EMAIL_REQUIRED, null);
        } else {
            if (!Pattern.matches(Regex.EMAIL_PATTERN, email)) {
                throw new ConstraintViolationException(ErrorMessage.EMAIL_FORMAT, null);
            }
        }

        if (SecurityUtil.getAuthenticatedUsername().equals(email) || SecurityUtil.getAuthenticatedRole().equals("[ROLE_ADMIN]")) {
            return authUserRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("email " + email +" not found"));
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    @Override
    public AuthUser findUserById(UUID userId) throws AccessDeniedException, EntityNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        UUID id;

        if (principal instanceof AuthUser authUser) {
            id = authUser.getId();
        } else if (principal instanceof User user) {
            AuthUser authUser = authUserRepository.findByEmail(user.getUsername())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
            id = authUser.getId();
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }

        String roles = authentication.getAuthorities().toString();

        if (id.equals(userId) || roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}