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
    private final EmailService emailService;

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

            // send email
            emailService.sendValidationEmail(user.getEmail(), verificationToken);

            // response to client
            return RegisterResponse.builder()
                    .message("Your registration has been successfully recorded. A validation email has been sent to you. Please check your inbox and follow the instructions to complete your registration.")
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

        // send email
        emailService.sendSuccessValidationEmail(user.getEmail());

        return MessageResponse.builder()
                .message("Your email is verified! You now have full access to your account.")
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

            // send email
            emailService.sendSuccessUpdatePasswordEmail(user.getEmail());

            return MessageResponse.builder()
                    .message("Your password has been updated successfully.")
                    .build();
        } else {
            throw new AccessDeniedException("You are not authorized to access this resource");
        }
    }

    public MessageResponse requestPasswordReset(String email) throws UsernameNotFoundException {
        // get user by email
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("No account was found associated with this email address. Please check the email you provided or consider creating a new account."));

        // create additional claims for the reset token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("type", "reset");

        // generate and save token
        String jwtToken = jwtService.generateToken(extraClaims, user, 15 * 60 * 1000);

        user.setResetToken(jwtToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(15));
        authUserRepository.save(user);

        // send password reset email
        emailService.sendPasswordResetEmail(user.getEmail(), jwtToken);

        // return token
        return MessageResponse.builder()
                .message("An email containing a link to reset your password has been sent to your address. Please check your inbox and follow the instructions.")
                .build();
    }

    public MessageResponse resetPassword(String token, String newPassword) throws IllegalStateException, ExpireTokenException, BadValidationArgumentException {
        // check token
        AuthUser user = authUserRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalStateException("Invalid or missing reset token"));

        if (user.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new ExpireTokenException("Token has expired");
        }

        if (newPassword == null || newPassword.length() < 8) {
            throw new BadValidationArgumentException("The new password must contain at least 8 characters.");
        }

        // update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);

        authUserRepository.save(user);

        // send email
        emailService.sendSuccessUpdatePasswordEmail(user.getEmail());

        return MessageResponse.builder()
                .message("Your password has been updated successfully. You can now use your new password to log in.")
                .build();
    }
}
