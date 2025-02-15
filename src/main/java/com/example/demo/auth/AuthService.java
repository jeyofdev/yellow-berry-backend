package com.example.demo.auth;

import com.example.demo.auth.model.RegisterRequest;
import com.example.demo.auth.model.RegisterResponse;
import com.example.demo.auth_user.AuthUser;
import com.example.demo.auth_user.AuthUserRepository;
import com.example.demo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) {
        if (authUserRepository.findByEmail(request.getEmail()).isEmpty()) {
            AuthUser user = AuthUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("ROLE_" + request.getRole().toUpperCase())
                    .build();

            authUserRepository.save(user);

            // response to client
            return RegisterResponse.builder()
                    .message("Your registration has been successfully recorded.")
                    .userId(String.valueOf(user.getId()))
                    .build();

        } else {
            throw new UsernameNotFoundException("Username already taken");
        }
    }
}
