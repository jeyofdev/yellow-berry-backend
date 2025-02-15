package com.example.demo.auth_user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public List<AuthUser> findAll()  {
        return authUserRepository.findAll();
    }

    public AuthUser findUserByEmail(String email) {
        return authUserRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("email " + email +" not found")
        );

    }

    public AuthUser findUserById(UUID userId) {
        return authUserRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User with id " + userId + " not found")
        );
    }
}