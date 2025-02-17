package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.auth.model.*;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        RegisterResponse registerResponse = authService.register(request, bindingResult);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        AuthResponse authenticationResponse = authService.login(request, bindingResult);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }

    @GetMapping("/validate-account")
    public ResponseEntity<MessageResponse> validateAccount(@RequestParam("verificationToken") String verificationToken) {
        MessageResponse messageResponse = authService.validateAccount(verificationToken);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<MessageResponse> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest updatePasswordRequest,
            BindingResult bindingResult
    ) {
        MessageResponse messageResponse = authService.updatePassword(
                updatePasswordRequest.getOldPassword(),
                updatePasswordRequest.getNewPassword(),
                bindingResult
        );

        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> requestPasswordReset(@RequestParam("email") String email) {
        MessageResponse requestPasswordResetResponse = authService.requestPasswordReset(email);
        return new ResponseEntity<>(requestPasswordResetResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @PermitAll
    public ResponseEntity<MessageResponse> resetPassword(
            @RequestParam("resetToken") String resetToken,
            @RequestParam("newPassword") String newPassword
    ) {
        MessageResponse messageResponse = authService.resetPassword(resetToken, newPassword);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }
}
