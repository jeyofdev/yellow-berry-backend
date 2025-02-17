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
public class ResetPasswordRequest {
    @ValidPassword
    private String newPassword;
}
