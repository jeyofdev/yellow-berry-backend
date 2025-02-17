package com.jeyofdev.yellow_berry.security.service;

import com.jeyofdev.yellow_berry.auth_user.AuthUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Getter
public class TokenService {
    private final JwtService jwtService;
    private String resetToken;

    /**
     * Generates a token with custom claims and expiration.
     */
    public String generateToken(AuthUser user, String type, Integer durationMillis) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", type);
        claims.put("id", user.getId());
        claims.put("role", user.getRole());

        return jwtService.generateToken(claims, user, durationMillis);
    }

    /**
     * Check if a token is expired by comparing with the current time.
     */
    public boolean isTokenExpired(LocalDateTime expirationTime) {
        System.out.println(expirationTime);
        return expirationTime.isBefore(LocalDateTime.now());
    }

    /**
     * Assign a verification token to a user for validate account
     */
    public void assignVerificationToken(AuthUser user) {
        String verificationToken = generateToken(user, "verification", 24 * 60 * 60 * 1000);

        user.setVerificationToken(verificationToken);
        user.setVerificationTokenExpiration(LocalDateTime.now().plusDays(1));
    }

    /**
     * Assign a reset token to a user for update password
     */
    public void assignResetToken(Map<String, Object> extraClaims, AuthUser user) {
        String resetToken = jwtService.generateToken(extraClaims, user, 15 * 60 * 1000);

        user.setResetToken(resetToken);
        user.setResetTokenExpiration(LocalDateTime.now().plusMinutes(15));

        this.resetToken = resetToken;
    }
}
