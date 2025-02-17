package com.jeyofdev.yellow_berry.auth;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetToken);

    void sendSuccessUpdatePasswordEmail(String toEmail);

    void sendValidationEmail(String email, String verificationToken);

    void sendSuccessValidationEmail(String email);
}
