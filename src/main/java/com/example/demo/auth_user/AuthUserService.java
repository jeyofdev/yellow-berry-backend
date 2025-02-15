package com.example.demo.auth_user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    public List<AuthUser> findAll()  {
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if(roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findAll();
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public AuthUser findUserByEmail(String email) {
        String authUserEmail  = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        System.out.println(authUserEmail);
        System.out.println(roles);

        if (authUserEmail.equals(email) || roles.equals("[ROLE_ADMIN]")) {
            return authUserRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("email " + email +" not found"));
        } else {
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    public AuthUser findUserById(UUID userId) {
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