package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.auth.model.*;
import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.auth_user.AuthUserRepository;
import com.jeyofdev.yellow_berry.exception.BadValidationArgumentException;
import com.jeyofdev.yellow_berry.exception.ExpireTokenException;
import com.jeyofdev.yellow_berry.exception.InvalidTokenException;
import com.jeyofdev.yellow_berry.exception.UsernameAlreadyTakenException;
import com.jeyofdev.yellow_berry.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public RegisterResponse register(RegisterRequest request) throws UsernameAlreadyTakenException {
        if (authUserRepository.findByEmail(request.getEmail()).isEmpty()) {
            AuthUser user = AuthUser.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("ROLE_" + request.getRole().toUpperCase())
                    .isVerified(false)
                    .build();

            // generate validation token
            String verificationToken = jwtService.generateToken(Map.of("type", "verification"), user, 24 * 60 * 60 * 1000);
            user.setVerificationToken(verificationToken);
            user.setVerificationTokenExpiration(LocalDateTime.now().plusDays(1));

            authUserRepository.save(user);

            // response to client
            return RegisterResponse.builder()
                    .message("Your registration has been successfully recorded.")
                    .userId(String.valueOf(user.getId()))
                    .build();

        } else {
            throw new UsernameAlreadyTakenException("Username already taken");
        }
    }

    public AuthResponse login(LoginRequest request) throws BadCredentialsException, UsernameNotFoundException {
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

        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException("Login failed. Please verify your credentials and try again.");
        }
    }

    public MessageResponse validateAccount(String verificationToken) throws InvalidTokenException, ExpireTokenException {
        if (verificationToken.isEmpty()) {
            throw new InvalidTokenException("The token must be provided");
        }

        AuthUser user = authUserRepository.findByVerificationToken(verificationToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid verification token"));

        // check if the token is expired
        if (user.getVerificationTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new ExpireTokenException("Verification token has expired");
        }

        // mark user as verified and save user
        user.setVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiration(null);

        authUserRepository.save(user);

        return MessageResponse.builder()
                .message("Your account is verified")
                .build();
    }

    public MessageResponse updatePassword(String oldPassword, String newPassword) throws IllegalStateException, BadValidationArgumentException, AccessDeniedException {
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if((roles.equals("[ROLE_ADMIN]")) || (roles.equals("[ROLE_USER]"))) {
            AuthUser user = authUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalStateException("Old password is incorrect.");
            }

            if (newPassword == null || newPassword.length() < 8) {
                throw new BadValidationArgumentException("The new password must contain at least 8 characters.");
            }

            // update password
            user.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(user);

            return MessageResponse.builder()
                    .message("Your password has been updated successfully.")
                    .build();
        } else {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
    }
}
