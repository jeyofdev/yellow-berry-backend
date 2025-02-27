package com.jeyofdev.yellow_berry.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jeyofdev.yellow_berry.core.model.AddressFormat;
import com.jeyofdev.yellow_berry.core.model.NameFormat;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProfilePreviewDTO(
        UUID id,
        String email,
        String role,
        NameFormat nameDetails,
        String phone,
        AddressFormat addressDetails
) {
}
