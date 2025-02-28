package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.auth.model.*;
import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import com.jeyofdev.yellow_berry.auth_user.AuthUserRepository;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.core.enums.RoleEnum;
import com.jeyofdev.yellow_berry.exception.BadValidationArgumentException;
import com.jeyofdev.yellow_berry.exception.ExpireTokenException;
import com.jeyofdev.yellow_berry.exception.InvalidTokenException;
import com.jeyofdev.yellow_berry.exception.UsernameAlreadyTakenException;
import com.jeyofdev.yellow_berry.security.service.JwtService;
import com.jeyofdev.yellow_berry.security.service.TokenService;
import com.jeyofdev.yellow_berry.util.Validator;
import jakarta.annotation.Nullable;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailServiceImpl;
    private final TokenService tokenService;

    @Override
    public RegisterResponse register(RegisterRequest request, BindingResult bindingResult) throws IllegalArgumentException, UsernameAlreadyTakenException {
        Validator.checkValidationErrorsExist(bindingResult);
        RoleEnum roleEnum = RoleEnum.fromString(request.getRole());

        // check if user exist
        if (authUserRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UsernameAlreadyTakenException(ErrorMessage.USERNAME_ALREADY_TAKEN);
        }

        // create user
        AuthUser user = AuthUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_" + request.getRole().toUpperCase())
                .isVerified(false)
                .build();

        // generate validation token and save user
        tokenService.assignVerificationToken(user);
        authUserRepository.save(user);

        // send email
        emailServiceImpl.sendValidationEmail(user.getEmail(), user.getVerificationToken());

        return RegisterResponse.builder()
                .message(ConfirmMessage.REGISTER)
                .userId(String.valueOf(user.getId()))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request, @Nullable BindingResult bindingResult) throws ConstraintViolationException, BadCredentialsException {
        if (bindingResult != null && bindingResult.hasErrors()) {
            // validation
            Validator.checkValidationErrorsExist(bindingResult);
        }

        // authentication
        authenticateUser(request.getEmail(), request.getPassword());

        // get user by email
        AuthUser user = authUserRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new BadCredentialsException(ErrorMessage.LOGIN_FAILED)
        );

        // extract user infos
        Map<String, Object> extraClaims = new HashMap<>();
        assert user != null;
        extraClaims.put("role", user.getRole());
        extraClaims.put("id", user.getId());

        // generate token with role
        String jwtToken = jwtService.generateToken(new HashMap<>(extraClaims), user, 60 * 60 * 1000);

        return AuthResponse.builder()
                .token(jwtToken)
                .message(ConfirmMessage.LOGIN)
                .build();
    }

    @Override
    public MessageResponse validateAccount(String verificationToken) throws InvalidTokenException, ExpireTokenException {
        if (verificationToken.isEmpty()) {
            throw new InvalidTokenException(ErrorMessage.TOKEN_MUST_BE_PROVIDED);
        }

        AuthUser user = authUserRepository.findByVerificationToken(verificationToken)
                .orElseThrow(() -> new InvalidTokenException(ErrorMessage.TOKEN_VERIFICATION_INVALID)
                );

        if (user.getVerificationTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new ExpireTokenException(ErrorMessage.TOKEN_VERIFICATION_EXPIRED);
        }

        // mark user as verified and save user
        user.setVerified(true);
        user.setVerificationToken(null);
        user.setVerificationTokenExpiration(null);

        authUserRepository.save(user);

        // send email
        emailServiceImpl.sendSuccessValidationEmail(user.getEmail());

        return MessageResponse.builder()
                .message(ConfirmMessage.VALIDATE_ACCOUNT)
                .build();
    }

    @Override
    public MessageResponse updatePassword(String oldPassword, String newPassword, BindingResult bindingResult) throws IllegalStateException, BadValidationArgumentException, UsernameNotFoundException, AccessDeniedException {
        String roles = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if ((roles.equals("[ROLE_ADMIN]")) || (roles.equals("[ROLE_USER]"))) {
            AuthUser user = authUserRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                    .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND));

            if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
                throw new IllegalStateException(ErrorMessage.OLD_BAD_PASSWORD);
            }

            // check if validation errors exists
            Validator.checkValidationErrorsExist(bindingResult);

            // update password
            user.setPassword(passwordEncoder.encode(newPassword));
            authUserRepository.save(user);

            // send email
            emailServiceImpl.sendSuccessUpdatePasswordEmail(user.getEmail());

            return MessageResponse.builder()
                    .message(ConfirmMessage.PASSWORD_UPDATED)
                    .build();
        } else {
            throw new AccessDeniedException(ErrorMessage.NO_AUTHORIZED);
        }
    }

    @Override
    public MessageResponse requestPasswordReset(String email) throws ConstraintViolationException, UsernameNotFoundException {
        if (email == null || email.isEmpty()) {
            throw new ConstraintViolationException(ErrorMessage.EMAIL_REQUIRED, null);
        }

        // get user by email
        Validator.emailFormat(email);
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.NO_USER_ASSOCIATED_EMAIL));

        // create additional claims for the reset token
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("type", "reset");

        // generate and save token
        tokenService.assignResetToken(extraClaims, user);
        authUserRepository.save(user);

        // send password reset email
        emailServiceImpl.sendPasswordResetEmail(user.getEmail(), tokenService.getResetToken());

        // return token
        return MessageResponse.builder()
                .message(ConfirmMessage.REQUEST_PASSWORD_RESET)
                .build();
    }

    @Override
    public MessageResponse resetPassword(String token, String newPassword) throws IllegalStateException, ExpireTokenException, BadValidationArgumentException {
        // check token
        AuthUser user = authUserRepository.findByResetToken(token)
                .orElseThrow(() -> new IllegalStateException(ErrorMessage.TOKEN_RESET_INVALID));

        if (tokenService.isTokenExpired(user.getResetTokenExpiration())) {
            throw new ExpireTokenException(ErrorMessage.TOKEN_RESET_EXPIRED);
        }

        Validator.passwordFormat(newPassword);

        // update password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiration(null);

        authUserRepository.save(user);

        // send email
        emailServiceImpl.sendSuccessUpdatePasswordEmail(user.getEmail());

        return MessageResponse.builder()
                .message(ConfirmMessage.PASSWORD_UPDATED_AFTER_FORGOT)
                .build();
    }

    /**
     * Check authentication credentials
     * if the user was found, check that the user is authorized to access protected resources
     */
    private void authenticateUser(String email, String password) throws BadCredentialsException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException exception) {
            throw new BadCredentialsException(ErrorMessage.LOGIN_FAILED);
        }
    }
}
