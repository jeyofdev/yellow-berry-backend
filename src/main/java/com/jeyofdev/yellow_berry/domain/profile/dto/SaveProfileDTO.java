package com.jeyofdev.yellow_berry.domain.profile.dto;

public record SaveProfileDTO(
        String firstname,
        String lastname,
        String phone,
        String address,
        String zipCode,
        String city,
        String department,
        String region
) {
}
