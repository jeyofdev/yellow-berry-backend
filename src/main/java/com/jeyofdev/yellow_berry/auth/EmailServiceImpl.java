package com.jeyofdev.yellow_berry.auth;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String fromEmail;

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        String resetUrl = MessageFormat.format("http://localhost:8080/api/v1/auth/reset-password?resetToken={0}", resetToken);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setSubject("Reset your password");
        message.setText(MessageFormat.format("To reset your password, please click on the following link : {0}", resetUrl));

        emailSender.send(message);
    }

    @Override
    public void sendSuccessUpdatePasswordEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setSubject("Password Updated");
        message.setText("Your password has been successfully changed. You can now use your new password to log in to your account. If you didn’t make this change or if you have any issues, feel free to contact us as soon as possible. We’re here to help you keep your account safe.");

        emailSender.send(message);
    }

    @Override
    public void sendValidationEmail(String email, String verificationToken) {
        String verificationUrl = MessageFormat.format("http://localhost:8080/api/v1/auth/validate-account?verificationToken={0}", verificationToken);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(fromEmail);
        message.setSubject("Verification of your account");
        message.setText(MessageFormat.format("Please click the following link to verify your account: {0}", verificationUrl));

        emailSender.send(message);
    }

    @Override
    public void sendSuccessValidationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(fromEmail);
        message.setSubject("Confirmation of your account validation");
        message.setText("We are pleased to inform you that your account has been successfully validated. You can now access all the features of our platform.");

        emailSender.send(message);
    }
}
