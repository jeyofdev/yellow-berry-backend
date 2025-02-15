package com.example.demo.auth;

import com.example.demo.auth.model.AuthResponse;
import com.example.demo.auth.model.LoginRequest;
import com.example.demo.auth.model.RegisterRequest;
import com.example.demo.auth.model.RegisterResponse;
import com.example.demo.auth_user.AuthUser;
import com.example.demo.auth_user.AuthUserRepository;
import com.example.demo.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public AuthResponse login(LoginRequest request) {

        // check credentials
        // if the user was found
        // check that the user is authorized to access protected resources
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            // get user by email
            AuthUser user = authUserRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found in Database"));

            // extract user infos
            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.getRole());
            extraClaims.put("id", user.getId());

            // generate token with role
            String jwtToken = jwtService.generateToken(new HashMap<>(extraClaims), user, 60 * 60 * 1000);
            return AuthResponse.builder()
                    .token(jwtToken)
                    .message("Logged In")
                    .build();

        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Login failed. Please verify your credentials and try again.");
        }
    }
}
