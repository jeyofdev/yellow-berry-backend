package com.jeyofdev.yellow_berry.auth.model;

import com.jeyofdev.yellow_berry.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {
    @ValidPassword(
            requiredMessage = "The old password field is required.",
            lengthMessage = "The old password must be contain between 8 and 16 characters.",
            formatMessage = "The old password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character."
    )
    private String oldPassword;

    @ValidPassword(
            requiredMessage = "The new password field is required.",
            lengthMessage = "The new password must be contain between 8 and 16 characters.",
            formatMessage = "The new password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character."
    )
    private String newPassword;
}