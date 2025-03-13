package com.jeyofdev.yellow_berry.auth;

import com.jeyofdev.yellow_berry.core.constant.ClientUrl;
import com.jeyofdev.yellow_berry.core.constant.Url;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
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
    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetUrl = MessageFormat.format("{0}/auth/reset-password?resetToken={1}", ClientUrl.getBaseUrl(), resetToken);

        sendEmail(
                email,
                "Reset your password",
                MessageFormat.format("To reset your password, please click on the following link : {0}", resetUrl)
        );
    }

    @Override
    public void sendSuccessUpdatePasswordEmail(String email) {
        sendEmail(
                email,
                "Password Updated",
                "Your password has been successfully changed. You can now use your new password to log in to your account. If you didn’t make this change or if you have any issues, feel free to contact us as soon as possible. We’re here to help you keep your account safe."
        );
    }

    @Override
    public void sendValidationEmail(String email, String verificationToken) {
        String verificationUrl = MessageFormat.format("{0}/auth/validate-account?verificationToken={1}", Url.getFullBaseUrl(), verificationToken);

        sendEmail(
                email,
                "Verification of your account",
                MessageFormat.format("Please click the following link to verify your account: {0}", verificationUrl)
        );
    }

    @Override
    public void sendSuccessValidationEmail(String email) {
        sendEmail(
                email,
                "Confirmation of your account validation",
                "We are pleased to inform you that your account has been successfully validated. You can now access all the features of our platform."
        );
    }

    private void sendEmail(String toEmail, String subject, String body) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setFrom(this.fromEmail);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);
    }
}
