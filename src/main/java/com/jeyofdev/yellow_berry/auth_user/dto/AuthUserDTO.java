package com.jeyofdev.yellow_berry.auth_user.dto;

import java.util.UUID;

public record AuthUserDTO(
        UUID id,
        String email,
        String role
) {
}