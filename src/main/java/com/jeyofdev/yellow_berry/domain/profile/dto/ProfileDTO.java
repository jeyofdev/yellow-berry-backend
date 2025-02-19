package com.jeyofdev.yellow_berry.domain.profile.dto;

import java.util.UUID;

public record ProfileDTO(
        UUID id,
        String firstname,
        String lastname,
        String phone,
        String address,
        String region,
        String department,
        String zipCode,
        String city
) {
}
