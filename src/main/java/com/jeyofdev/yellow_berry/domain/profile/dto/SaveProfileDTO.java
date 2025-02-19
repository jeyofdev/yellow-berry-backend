package com.jeyofdev.yellow_berry.domain.profile.dto;

public record SaveProfileDTO(
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
