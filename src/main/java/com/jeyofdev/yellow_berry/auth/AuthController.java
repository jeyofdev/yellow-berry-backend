package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.auth.model.AuthResponse;
import com.jeyofdev.yellow_berry.auth.model.LoginRequest;
import com.jeyofdev.yellow_berry.auth.model.RegisterRequest;
import com.jeyofdev.yellow_berry.auth.model.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse registerResponse = authService.register(request);
        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse authenticationResponse = authService.login(request);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.OK);
    }
}
