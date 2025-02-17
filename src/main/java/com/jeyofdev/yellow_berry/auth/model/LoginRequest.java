package com.jeyofdev.yellow_berry.auth.model;

import com.jeyofdev.yellow_berry.annotation.ValidEmail;
import com.jeyofdev.yellow_berry.annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @ValidEmail
    private String email;

    @ValidPassword
    private String password;
}
