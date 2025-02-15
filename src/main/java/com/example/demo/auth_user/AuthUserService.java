package com.example.demo.auth_user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserRepository authUserRepository;

    public List<AuthUser> findAll() {
        return authUserRepository.findAll();
    }

    public AuthUser add(AuthUser authUser) {
        return authUserRepository.save(authUser);
    }
}
